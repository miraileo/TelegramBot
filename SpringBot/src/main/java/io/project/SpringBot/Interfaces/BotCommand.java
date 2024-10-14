package io.project.SpringBot.Interfaces;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface BotCommand {
    void execute(Update update) throws TelegramApiException;
}