package io.project.SpringBot.Backend;

import io.project.SpringBot.Interfaces.BotCommand;
import io.project.SpringBot.MYAmazingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


public class InlineRaspisanieButtons implements BotCommand {
    private final MYAmazingBot bot;

    public InlineRaspisanieButtons(MYAmazingBot bot) {
        this.bot = bot;
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        long chatId = update.getMessage().getChatId();

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("На какой день вам нужно расписание:");

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> todayButtonRow = new ArrayList<>();
        todayButtonRow.add(InlineKeyboardButton.builder()
                .text("На сегодня")
                .callbackData("raspisanie_today")
                .build());

        rowsInline.add(todayButtonRow);

        String[] daysOfWeek = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};
        String[] callbackD = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};
        for (int i = 0; i < daysOfWeek.length; i++) {
            List<InlineKeyboardButton> dayButtonRow = new ArrayList<>();
            dayButtonRow.add(InlineKeyboardButton.builder()
                    .text(daysOfWeek[i])
                    .callbackData("raspisanie_" + callbackD[i])
                    .build());
            rowsInline.add(dayButtonRow);
        }

        List<InlineKeyboardButton> weekButtonRow = new ArrayList<>();
        weekButtonRow.add(InlineKeyboardButton.builder()
                .text("Всю неделю")
                .callbackData("raspisanie_week")
                .build());

        rowsInline.add(weekButtonRow);

        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        bot.execute(message);
    }
}
