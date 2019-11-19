package com.fiap.friendsecret.entities;

import com.pengrad.telegrambot.model.User;
import java.util.HashMap;
import java.util.Map;

public class Manager {
    static Map<String, Object> response = new HashMap<>();
    static String questionBot;
    private String answerUser;
    private User owner;
    private Person user = new Person();

    public void setMessage(String answer, User owner) {
        this.answerUser = answer;
        this.owner = owner;
    }

    public void setResponse(Map<String, Object> response) {
        Manager.response = response;
    }

    public String checkMessage() {
        user.setUser(owner);

        if ( questionBot == null) {
            questionBot = "Olá, seja Bem Vindo! Você gostatia de participar?";
        } else {
            try {
                Object result = response.get(questionBot);
                questionBot = ((HashMap) result).get(answerUser.toUpperCase()).toString();
            } catch (Exception e) {
                questionBot = "Desculpe, não entendi sua resposta \n";
                e.getMessage();
            }
        }
        return questionBot;
    }
}
