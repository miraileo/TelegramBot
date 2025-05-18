package io.project.SpringBot.Backend;

import io.project.SpringBot.Interfaces.ButtonsProvider;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class ScheduleButtons implements ButtonsProvider {

    @Override
    public SendMessage getButtons(Update update) {
        String chatId = String.valueOf(update.getMessage().getChatId());
        SendMessage msg = new SendMessage(chatId, "На какой день вам нужно расписание:");

        List<List<InlineKeyboardButton>> buttons = new java.util.ArrayList<>();
        buttons.add(List.of(button("На сегодня", "raspisanie_today")));

        String[] days = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};
        String[] data = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};

        IntStream.range(0, days.length)
                .mapToObj(i -> List.of(button(days[i], "raspisanie_" + data[i])))
                .forEach(buttons::add);

        buttons.add(List.of(button("Всю неделю", "raspisanie_week")));

        msg.setReplyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build());
        return msg;
    }

    private InlineKeyboardButton button(String text, String data) {
        return InlineKeyboardButton.builder().text(text).callbackData(data).build();
    }
}
