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

	private static HashMap<Integer, String> userLatestAnswer = new HashMap<>();

	private static HashMap<Integer, String> recorded = new HashMap<>();
	
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
		if(answerUser != null && answerUser.startsWith("/"))
			userLatestStep = answerUser;

		Object options = response.get(userLatestStep);
		String result = parseResult(options, answerUser);

		userLatestAnswer.put(owner.id(), answerUser);

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
		user.setUser(owner);
		((JSONArray) options).forEach(item -> {
			if (((JSONObject) item).containsKey(answerUser.toUpperCase())) {
				questionBot.put(owner.id(), ((JSONObject) item).get(answerUser.toUpperCase()).toString());
			}
		});
		return buildMessage(questionBot.get(owner.id()));
	}

	private String greeting() {
		user.setUser(owner);
		answerUser = null;
		questionBot.put(user.getId(), WELCOME);

		return buildMessage("Olá, " + owner.firstName() + "! " + WELCOME);
	}

	private String flowSimple(Object options) {
		user.setUser(owner);
		questionBot.put(user.getId(), ((JSONArray) options).get(0).toString());
		return buildMessage(owner.firstName() + ", " + questionBot.get(owner.id()));
	}

	private String notFound() {
		user.setUser(owner);
		questionBot.put(user.getId(), "Desculpe, não entendi sua resposta");
		return buildMessage(questionBot.get(owner.id()));
	}

	private String buildMessage(String message) {
		if(message != null && message.contains("@record")) {
			message = message.replaceAll("@record", answerUser);
			recorded.put(user.getId(), answerUser);
		}
		if(message != null && message.contains("!id")) {
			message = message.replaceAll("!id", user.getId() + "");
		}
		if(message != null && message.contains("#find")) {
			message = message.replaceAll("#find", recorded.get(Integer.parseInt(userLatestAnswer.get(user.getId()))));
		}

		return message;
	}

}
