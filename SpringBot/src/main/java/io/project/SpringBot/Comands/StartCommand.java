package io.project.SpringBot.Comands;

import io.project.SpringBot.Interfaces.BotCommand;
import io.project.SpringBot.MYAmazingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class StartCommand implements BotCommand {

    private final MYAmazingBot bot;

    public StartCommand(MYAmazingBot bot) {
        this.bot = bot;
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        long chatId = update.getMessage().getChatId();
        String response = "Привет я Каи бот напиши /raspisanie чтобы выбрать день на который тебе нужно расписание, /document чтобы получить нужный тебе документ";
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(response);
        bot.execute(message);
    }
}