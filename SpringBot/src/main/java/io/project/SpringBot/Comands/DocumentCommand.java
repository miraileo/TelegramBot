package io.project.SpringBot.Comands;

import io.project.SpringBot.Configuration.BotConfiguration;
import io.project.SpringBot.Interfaces.BotCommand;
import io.project.SpringBot.Backend.FileDownloader;
import io.project.SpringBot.MYAmazingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;

public class DocumentCommand implements BotCommand {
    private final BotConfiguration botConfiguration;
    private final MYAmazingBot bot;
    private String fileUrl;
    private String fileName;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public DocumentCommand(MYAmazingBot bot, BotConfiguration botConfiguration) {
        this.bot = bot;
        this.botConfiguration = botConfiguration;
    }

    private void chooseFileUrl(){
        fileUrl = botConfiguration.getDocuments().getOrDefault(fileName, null);
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        try {
            chooseFileUrl();
            File outputFile = FileDownloader.downloadFile(fileUrl, fileName+".pdf");
            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(String.valueOf(chatId));
            sendDocument.setDocument(new org.telegram.telegrambots.meta.api.objects.InputFile(outputFile));
            bot.execute(sendDocument);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
