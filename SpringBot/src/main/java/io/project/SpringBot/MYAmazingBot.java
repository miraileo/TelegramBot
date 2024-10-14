package io.project.SpringBot;
import io.project.SpringBot.Configuration.BotConfiguration;
import io.project.SpringBot.Data.Raspisanie;
import io.project.SpringBot.Dispatchers.CommandDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Configuration
public class MYAmazingBot extends TelegramLongPollingBot {
    private final BotConfiguration botConfiguration;
    private final CommandDispatcher commandDispatcher;
    private final Raspisanie raspisanie;

    @Autowired
    public MYAmazingBot(BotConfiguration botConfig, Raspisanie raspisanie) {
        this.botConfiguration = botConfig;
        this.raspisanie = raspisanie;
        this.commandDispatcher = new CommandDispatcher(this, botConfiguration, raspisanie);
    }

    @Override
    public String getBotUsername() {
        return botConfiguration.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfiguration.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            commandDispatcher.handleCommand(update);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
