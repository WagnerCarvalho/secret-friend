package com.fiap.friendsecret.service;

import com.fiap.friendsecret.entities.Manager;
import org.springframework.stereotype.Service;

@Service
public class Search {

    public String checkMessage(String msg) {
        Manager manager = new Manager();
        manager.setMessage(msg);
        return manager.checkMessage();
    }

}
