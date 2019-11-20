package com.fiap.friendsecret.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Bot {

    @Value("${telegram.token}")
    private String TOKEN;

    @Autowired
    private Search search;
    private GetUpdatesResponse updatesResponse;
    private SendResponse sendResponse;
    private BaseResponse baseResponse;

    public void startBot() {
        TelegramBot bot = TelegramBotAdapter.build(TOKEN);

        int m=0;
        while (true){
            updatesResponse =  bot.execute(new GetUpdates().limit(100).offset(m));

            List<Update> updates = updatesResponse.updates();

            for (Update update : updates) {
                m = update.updateId()+1;
                baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
                sendResponse = bot.execute(new SendMessage(update.message().chat().id(), search.checkMessage(update.message())));
            }
        }
    }
}
