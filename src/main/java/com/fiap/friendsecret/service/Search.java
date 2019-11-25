package com.fiap.friendsecret.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pengrad.telegrambot.model.Message;

@Service
public class Search {

	@Value("${telegram.welcome}") String WELCOME;
	
    public String checkMessage(Message message) {
        Manager manager = new Manager(message.text(), message.from(), WELCOME);
        return manager.checkMessage();
    }

}
