package io.project.SpringBot.bot;
import io.project.SpringBot.Configuration.BotConfiguration;
import io.project.SpringBot.Dispatchers.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class MYAmazingBot extends TelegramLongPollingBot {
    @Autowired
    private BotConfiguration configuration;
    @Autowired
    private CommandDispatcher commandDispatcher;

    @Autowired
    public MYAmazingBot(BotConfiguration configuration) {
        super(configuration.getBotToken());
    }

    @Override
    public String getBotUsername() {return configuration.getBotName();}

    @Override
    public void onUpdateReceived(Update update) {
        try {
            commandDispatcher.handleCommand(update, this);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Something gone wrong", e);
        }
    }
}
