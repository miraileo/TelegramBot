package io.project.SpringBot.Interfaces;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ButtonsProvider {
    SendMessage getButtons(Update update);
}
