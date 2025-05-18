package io.project.SpringBot.Dispatchers;

import io.project.SpringBot.Interfaces.BotCommand;
import io.project.SpringBot.Interfaces.ButtonsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class CommandDispatcher {

    @Autowired
    private ButtonsProvider buttonsProvider;

    @Autowired @Qualifier("startCommand")
    private BotCommand startCommand;

    @Autowired @Qualifier("scheduleCommand")
    private BotCommand scheduleCommand;

    public void handleCommand(Update update, TelegramLongPollingBot bot) throws TelegramApiException {
        if (update.hasMessage() && update.getMessage().hasText()) {
            switch (update.getMessage().getText()) {
                case "/start" -> bot.execute(startCommand.getMessage(update));
                case "/raspisanie" -> bot.execute(buttonsProvider.getButtons(update));
            }
        } else if (update.hasCallbackQuery()) {
            var message = update.getCallbackQuery().getMessage();
            String chatId = String.valueOf(message.getChatId());
            int messageId = message.getMessageId();

            bot.execute(DeleteMessage.builder().chatId(chatId).messageId(messageId).build());

            var waitMessage = SendMessage.builder().chatId(chatId).text("Пожалуйста, подождите...").build();
            int waitMessageId = bot.execute(waitMessage).getMessageId();

            bot.execute(scheduleCommand.getMessage(update));

            bot.execute(DeleteMessage.builder().chatId(chatId).messageId(waitMessageId).build());
        }
    }
}
