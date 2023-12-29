package com.example.example_bot.handler.impl;

import com.example.example_bot.enums.ConversationState;
import com.example.example_bot.handler.UserRequestHandler;
import com.example.example_bot.helper.KeyboardHelper;
import com.example.example_bot.model.UserRequest;
import com.example.example_bot.model.UserSession;
import com.example.example_bot.service.TelegramService;
import com.example.example_bot.service.UserSessionService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;


@Component
public class TextEnteredHandler extends UserRequestHandler {

    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;
    private final UserSessionService userSessionService;

    public TextEnteredHandler(TelegramService telegramService, KeyboardHelper keyboardHelper, UserSessionService userSessionService) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
        this.userSessionService = userSessionService;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isTextMessage(userRequest.getUpdate())
                && ConversationState.WAITING_FOR_TEXT.equals(userRequest.getUserSession().getState());
    }

    @Override
    public void handle(UserRequest userRequest) {
        ReplyKeyboardMarkup replyKeyboardMarkup = keyboardHelper.buildMainMenu();
        telegramService.sendMessage(userRequest.getChatId(),"Дякую, ваше звернення було зареєстровано!", replyKeyboardMarkup);

        String text = userRequest.getUpdate().getMessage().getText();

        UserSession session = userRequest.getUserSession();
        session.setText(text);
        session.setState(ConversationState.CONVERSATION_STARTED);
        userSessionService.saveSession(userRequest.getChatId(), session);
    }

    @Override
    public boolean isGlobal() {
        return false;
    }
}
