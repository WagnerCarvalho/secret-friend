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
    static HashMap<Integer, String> questionBot = new HashMap<>();
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
        //simplificar IF
        String result;
        if (questionBot.size() == 0) {
            result = greeting(welcome);
        } else if (questionBot.containsKey(owner.id())) {
            result = parseResult(response.get(questionBot.get(owner.id())), answerUser, welcome);
        } else {
            result = greeting(welcome);
        }
        return result;
    }

    private String parseResult(Object options, String answerUser, String welcome) {
        try {
            switch (((JSONArray) options).size()) {
                case 0:
                    return notFound();
                case 1:
                    return flowSimple(options);
                default:
                    return flowMultiple(options);
            }
        } catch (Exception e) {
            return greeting(welcome);
        }
    }

    private String flowMultiple(Object options) {
        ((JSONArray) options).forEach(item -> {
            if (((JSONObject) item).containsKey(answerUser.toUpperCase())) {
                questionBot.put(owner.id(), ((JSONObject) item).get(answerUser.toUpperCase()).toString());
            }
        });
        return questionBot.get(owner.id());
    }

    private String greeting(String welcome) {
        user.setUser(owner);
        answerUser = null;
        questionBot.put(user.getId(), welcome);

        return "Olá, " + owner.firstName() + "! " + questionBot.values();
    }

    private String flowSimple(Object options) {
        questionBot.put(user.getId(), ((JSONArray) options).get(0).toString());
        return owner.firstName() + ", " + answerUser + "!!! " + questionBot.get(owner.id());
    }

    private String notFound() {
        questionBot.put(user.getId(), "Desculpe, não entendi sua resposta");
        return questionBot.get(owner.id());
    }
}

