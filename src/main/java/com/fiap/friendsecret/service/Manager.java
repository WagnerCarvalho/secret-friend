package com.fiap.friendsecret.service;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fiap.friendsecret.entities.Person;
import com.pengrad.telegrambot.model.User;

/**
 * Gerencia as sequência de informações resultantes da entrada de dados, ou
 * seja, para cada pergunta, qual a próxima ação e mensagem que o bot enviará para o usuário
 * 
 * @author softon
 *
 */
public class Manager {

	private static Map<String, Object> response = new HashMap<>();
	private static HashMap<Integer, String> questionBot = new HashMap<>();
	
	private static String WELCOME;
	
	private Person user;
	private String answerUser;
	private User owner;

    public Manager(String answer, User owner) {
		this.user =  new Person();
		this.answerUser = answer;
		this.owner = owner;
	}
    
    /**
     * Metodo utilizado para inflar o mapa de opções de fluxo de conversa com o bot
     * @param response
     */
	public static void setResponse(Map<String, Object> response) {
		Manager.response = response;
	}
	
	public static void setDefaultResponse(String welcome) {
		WELCOME = welcome;
	}
	
	public String checkMessage() {
		String userLatestStep = questionBot.get(owner.id());
		Object options = response.get(userLatestStep);
		String result = parseResult(options, answerUser);
		return result;
	}

	private String parseResult(Object options, String answerUser) {
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
			return greeting();
		}
	}

	@SuppressWarnings("unchecked")
	private String flowMultiple(Object options) {
		((JSONArray) options).forEach(item -> {
			if (((JSONObject) item).containsKey(answerUser.toUpperCase())) {
				questionBot.put(owner.id(), ((JSONObject) item).get(answerUser.toUpperCase()).toString());
			}
		});
		return questionBot.get(owner.id());
	}

	private String greeting() {
		user.setUser(owner);
		answerUser = null;
		questionBot.put(user.getId(), WELCOME);

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
