package io.project.SpringBot.Dispatchers;

import io.project.SpringBot.Backend.InlineDocumentButtons;
import io.project.SpringBot.Backend.InlineRaspisanieButtons;
import io.project.SpringBot.Comands.DocumentCommand;
import io.project.SpringBot.Comands.PhotoCommand;
import io.project.SpringBot.Comands.StartCommand;
import io.project.SpringBot.Configuration.BotConfiguration;
import io.project.SpringBot.Data.Raspisanie;
import io.project.SpringBot.Interfaces.BotCommand;
import io.project.SpringBot.MYAmazingBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;
@Configuration
public class CommandDispatcher {

    private final Map<String, BotCommand> commandMap = new HashMap<>();
    private final DocumentCommand documentCommand;
    private final PhotoCommand photoCommand;
    private final MYAmazingBot bot;
    private final Raspisanie raspisanie;
    @Autowired
    public CommandDispatcher(MYAmazingBot bot, BotConfiguration botConfiguration, Raspisanie raspisanie) {
        this.bot = bot;
        this.raspisanie = raspisanie;
        commandMap.put("/start", new StartCommand(bot));
        commandMap.put("/document", new InlineDocumentButtons(bot));
        commandMap.put("/raspisanie", new InlineRaspisanieButtons(bot));
        photoCommand = new PhotoCommand(bot, raspisanie);
        documentCommand = new DocumentCommand(bot, botConfiguration);
    }

    public void handleCommand(Update update) throws TelegramApiException {
        BotCommand command;
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();

            command = commandMap.get(messageText);
            if (command != null) {
                command.execute(update);
            } else {
                System.out.println("Unknown command: " + messageText);
            }
        }
        else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            if(callbackData.contains("raspisanie")){
                command = photoCommand;
                photoCommand.setDay(callbackData);
            }
            else if(callbackData.contains("document")) {
                command = documentCommand;
                documentCommand.setFileName(callbackData);
            }
            else return;
            if (command != null){
                long chatId = update.getCallbackQuery().getMessage().getChatId();
                int messageId = update.getCallbackQuery().getMessage().getMessageId();
                DeleteMessage deleteMessage = new DeleteMessage();
                deleteMessage.setChatId(String.valueOf(chatId));
                deleteMessage.setMessageId(messageId);
                bot.execute(deleteMessage);
                SendMessage waitMessage = new SendMessage();
                waitMessage.setChatId(String.valueOf(chatId));
                waitMessage.setText("Пожалуйста, подождите...");
                var sentWaitMessage = bot.execute(waitMessage);
                command.execute(update);
                DeleteMessage deleteWaitMessage = new DeleteMessage();
                deleteWaitMessage.setChatId(String.valueOf(chatId));
                deleteWaitMessage.setMessageId(sentWaitMessage.getMessageId());
                bot.execute(deleteWaitMessage);
            }
        }
    }
}
