package com.fiap.friendsecret.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fiap.friendsecret.Memory;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

/**
 * Classe é responável por todo o controle de execução do bot
 * 
 * @author Wagner, Rodrigo, Eduardo
 *
 */
@Component
public class FriendSecretBot {

	@Autowired
	private Memory memory;
    
	@Autowired
	TelegramBot bot;

	@Autowired
    private Search search;
    private GetUpdatesResponse updatesResponse;
    SendResponse sendResponse;
    BaseResponse baseResponse;
    
    private int m=0;
    
    /**
     * Inicializa todas as inforações necessárias para o funcionamento do bot
     */
    @PostConstruct
    public void init() {
    	memory.loadResponse();
    }

    /**
     * Executa bot, recebendo mensagens e respondendo-as
     */
    @Scheduled(fixedDelay=5000)
    public void runBot() {
        updatesResponse =  bot.execute(new GetUpdates().limit(100).offset(m));

        List<Update> updates = updatesResponse.updates();

        //verifica todas as interações pendentes
        for (Update update : updates) {
            m = update.updateId()+1;
            baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
            sendResponse = bot.execute(new SendMessage(update.message().chat().id(), search.checkMessage(update.message())));
        }
    }
}
