package com.fiap.friendsecret.service;

import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.model.Message;

import java.nio.charset.Charset;

@Service
public class Search {

    public String checkMessage(Message message) {
        Manager manager = new Manager(message.text(), message.from());
        return manager.checkMessage();
    }

}
