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

public class InlineDocumentButtons implements BotCommand {
    private final MYAmazingBot bot;

    public InlineDocumentButtons(MYAmazingBot bot){
        this.bot = bot;
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        long chatId = update.getMessage().getChatId();

        // Создаем сообщение с кнопками для выбора документа
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите документ:");

        // Создаем инлайн-клавиатуру
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        // Кнопка 1 - Бланк ответа
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(InlineKeyboardButton.builder()
                .text("Бланк ответа")
                .callbackData("document_blank_otveta")
                .build());

        // Кнопка 2 - Другой документ
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(InlineKeyboardButton.builder()
                .text("Другой документ")
                .callbackData("document_other_document")
                .build());


        // Добавляем строки в список клавиатуры
        rowsInline.add(row1);
        rowsInline.add(row2);

        // Устанавливаем клавиатуру в сообщение
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        // Отправляем сообщение
        bot.execute(message);
    }
}
