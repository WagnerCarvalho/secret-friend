package com.fiap.friendsecret.service;

import com.fiap.friendsecret.entities.Manager;
import com.pengrad.telegrambot.model.Message;
import org.springframework.stereotype.Service;

@Service
public class Search {

    public String checkMessage(Message message) {
        Manager manager = new Manager();
        manager.setMessage(message.text(), message.from());
        return manager.checkMessage();
    }

}
