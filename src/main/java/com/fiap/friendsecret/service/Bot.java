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

    //objeto responsável por receber as mensagens
    private GetUpdatesResponse updatesResponse;

    //objeto responsável por gerenciar o envio de respostas
    private SendResponse sendResponse;

    //objeto responsável por gerenciar o envio de ações do chat
    private BaseResponse baseResponse;


    public void startBot() {
        TelegramBot bot = TelegramBotAdapter.build(TOKEN);

        //controle de off-set, isto é, a partir deste ID será lido as mensagens pendentes na fila
        int m=0;

        //loop infinito pode ser alterado por algum timer de intervalo curto
        while (true){

            //executa comando no Telegram para obter as mensagens pendentes a partir de um off-set (limite inicial)
            updatesResponse =  bot.execute(new GetUpdates().limit(100).offset(m));

            //lista de mensagens
            List<Update> updates = updatesResponse.updates();

            //análise de cada ação da mensagem
            for (Update update : updates) {

                //atualização do off-set
                m = update.updateId()+1;


                //envio de "Escrevendo" antes de enviar a resposta
                baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));

                //verificação de ação de chat foi enviada com sucesso
                System.out.println("Resposta de Chat Action Enviada?" + baseResponse.isOk());

                //envio da mensagem de resposta
                sendResponse = bot.execute(new SendMessage(update.message().chat().id(), search.checkMessage(update.message().text())));

                //verificação de mensagem enviada com sucesso
                System.out.println("Mensagem Enviada?" +sendResponse.isOk());

            }

        }
    }

}
