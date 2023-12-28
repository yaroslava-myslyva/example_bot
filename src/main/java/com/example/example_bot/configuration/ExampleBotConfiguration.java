package com.example.example_bot.configuration;

import com.example.example_bot.bot.ExampleBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class ExampleBotConfiguration {

    @Bean
    public TelegramBotsApi telegramBotsApi(ExampleBot exampleBot) throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(exampleBot);
        return api;
    }
}
