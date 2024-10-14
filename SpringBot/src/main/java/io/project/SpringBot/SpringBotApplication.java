package io.project.SpringBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class SpringBotApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringBotApplication.class, args);

		try {
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

			MYAmazingBot myAmazingBot = context.getBean(MYAmazingBot.class);

			botsApi.registerBot(myAmazingBot);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
