package io.project.SpringBot.Configuration;

import io.project.SpringBot.Data.Raspisanie;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class BotConfiguration {

    private Raspisanie raspisanie = new Raspisanie();

    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;
    @Value("${bot.document.blank_otveta}")
    private String blank_otveta;

    Map<String, String> documents = new HashMap<>();



    @PostConstruct
    public void init(){
        raspisanie.getRaspisanie();
        documents.put("document_blank_otveta", blank_otveta);
    }


    public Map<String, String> getDocuments() {
        return documents;
    }

    public String getBotName() {
        return botName;
    }
    public String getBotToken() {
        return botToken;
    }
}
