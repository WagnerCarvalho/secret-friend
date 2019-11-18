package com.fiap.friendsecret.entities;

import java.util.HashMap;
import java.util.Map;

public class Manager {
    static Map<String, String> response = new HashMap<>();
    static String questionBot;
    private String answerUser;


    public void setMessage(String answer) {
        this.answerUser = answer;
    }

    public void setResponse(Map<String, String> response) {
        Manager.response = response;
    }

    public String checkMessage() {
        if ( questionBot == null) {
            questionBot = "Olá, seja Bem Vindo! Você gostatia de participar?";
        } else {
            questionBot = response.get(this.answerUser.toUpperCase());;
        }
        return questionBot;
    }
}
