package com.fiap.friendsecret.service;

import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.model.Message;

import java.nio.charset.Charset;

/**
 * CLasse responsável pelas buscas das opções para as ações do usuário
 */
@Service
public class Search {

    /**
     * Para a mensagem recebida, checka e retorna a próxima ação
     * @param message
     * @return
     */
    public String checkMessage(Message message) {
        Manager manager = new Manager(message.text(), message.from());
        return manager.checkMessage();
    }

}
