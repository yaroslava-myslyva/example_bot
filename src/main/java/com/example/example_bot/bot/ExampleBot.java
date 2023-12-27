package com.example.example_bot.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ExampleBot extends TelegramLongPollingBot {

   public ExampleBot(String botToken) {
       super(botToken);
   }

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return null;
    }
}
