package com.fiap.friendsecret.service;

import com.fiap.friendsecret.entities.Manager;
import com.pengrad.telegrambot.model.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.nio.charset.Charset;

@Service
public class Search {

    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private static final Charset ISO = Charset.forName("ISO-8859-1");

    @Value("${telegram.welcome}")
    private String WELCOME;

    public String checkMessage(Message message) {
        Manager manager = new Manager();
        manager.setMessage(message.text(), message.from());
        return manager.checkMessage(new String(WELCOME.getBytes(ISO), UTF_8));
    }

}
