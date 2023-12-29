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


import java.util.List;

@Component
public class INeedHelpHandler extends UserRequestHandler {

    public static String I_NEED_HELP = "❗️Потрібна допомога";
    public static List<String> cities = List.of("Київ", "Львів");

    private final TelegramService telegramService;
    private final KeyboardHelper keyboardHelper;
    private final UserSessionService userSessionService;

    public INeedHelpHandler(TelegramService telegramService, KeyboardHelper keyboardHelper, UserSessionService userSessionService) {
        this.telegramService = telegramService;
        this.keyboardHelper = keyboardHelper;
        this.userSessionService = userSessionService;
    }

    @Override
    public boolean isApplicable(UserRequest userRequest) {
        return isTextMessage(userRequest.getUpdate(), I_NEED_HELP);
    }

    @Override
    public void handle(UserRequest userRequest) {
        ReplyKeyboardMarkup replyKeyboardMarkup = keyboardHelper.buildCitiesMenu(cities);
        telegramService.sendMessage(userRequest.getChatId(),"Оберіть місто або опишіть вручну⤵️", replyKeyboardMarkup);

        UserSession userSession = userRequest.getUserSession();
        userSession.setState(ConversationState.WAITING_FOR_CITY);
        userSessionService.saveSession(userSession.getChatId(), userSession);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }

}
