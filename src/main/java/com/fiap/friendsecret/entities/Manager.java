package com.fiap.friendsecret.entities;

import com.pengrad.telegrambot.model.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class Manager {

    static Map<String, Object> response = new HashMap<>();
    static String questionBot;
    static Person user = new Person();
    private String answerUser;
    private User owner;

    public void setMessage(String answer, User owner) {
        this.answerUser = answer;
        this.owner = owner;
    }

    public void setResponse(Map<String, Object> response) {
        Manager.response = response;
    }

    public String checkMessage(String welcome) {
        return questionBot == null? greeting(welcome): parseResult(response.get(questionBot), answerUser);
    }

    private String parseResult(Object options, String answerUser) {
        switch (((JSONArray) options).size()) {
            case 0:
                return notFound();
            case 1:
                return flowSimple(options);
            default:
                return flowMultiple(options);
        }
    }

    private String greeting(String welcome) {
        user.setUser(owner);
        answerUser = null;
        questionBot = welcome;
        return "Olá, " + user.getFirstName() + "! " + questionBot;
    }

    private String flowSimple(Object options) {
        questionBot = ((JSONArray) options).get(0).toString();
        return user.getFirstName() + ", " + answerUser + "!!! " + questionBot;
    }

    private String notFound() {
        return questionBot = "Desculpe, não entendi sua resposta";
    }

    private String flowMultiple(Object options) {
        ((JSONArray) options).forEach(item -> {
            if (((JSONObject) item).containsKey(answerUser.toUpperCase())) {
                questionBot = ((JSONObject) item).get(answerUser.toUpperCase()).toString();
            }
        });
        return questionBot;
    }

}
