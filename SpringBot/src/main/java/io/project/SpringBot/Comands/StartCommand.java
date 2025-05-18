package io.project.SpringBot.Comands;

import io.project.SpringBot.Interfaces.BotCommand;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class StartCommand implements BotCommand {
    @Override
    public SendMessage getMessage(Update update) {
        long chatId = update.getMessage().getChatId();
        String response = "Привет я Каи бот напиши /raspisanie чтобы выбрать день на который тебе нужно расписание";
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(response);
        return message;
    }
}