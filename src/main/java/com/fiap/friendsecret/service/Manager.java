package com.fiap.friendsecret.service;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
	
	private String answerUser;
	private User owner;

    public Manager(String answer, User owner) {
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
		//verifica a ultima acao do usuario, caso haja
		String userLatestStep = questionBot.get(owner.id());

		//se a amensagem comeca com '/', trata-se de um item de menu sendo acionado
		if(answerUser != null && answerUser.startsWith("/"))
			userLatestStep = answerUser;

		//busca no mapa de respostas as opcoes para o usuario
		Object options = response.get(userLatestStep);

		//obtem a mensagem a ser devolvida para o usuario
		String result = parseResult(options, answerUser);

		//mantem a ultima resposta do usuario
		userLatestAnswer.put(owner.id(), answerUser);

		return result;
	}

	private String parseResult(Object options, String answerUser) {
		//verifica qual o tipo de resposta será dada pelo bot
		try {
			switch (((JSONArray) options).size()) {
			case 0:
				//acao sem resposta predefinida
				return notFound();
			case 1:
				//resposta simples (uma opcao)
				return flowSimple(options);
			default:
				//resposta multipla (diversas opcoes)
				return flowMultiple(options);
			}
		} catch (Exception e) {
			//se nao há opcao para a mensagem do usuario, trata-se de uma acao desconhecida
			//o sistema devolve o texto padrao
			return greeting();
		}
	}

	@SuppressWarnings("unchecked")
	private String flowMultiple(Object options) {
		//monta opcoes multiplas e devolve resposta para o usuario
		((JSONArray) options).forEach(item -> {
			if (((JSONObject) item).containsKey(answerUser.toUpperCase())) {
				questionBot.put(owner.id(), ((JSONObject) item).get(answerUser.toUpperCase()).toString());
			}
		});
		return buildMessage(questionBot.get(owner.id()));
	}

	private String greeting() {
		//gera texto padrao para o usuario
		answerUser = null;
		questionBot.put(owner.id(), WELCOME);

		return buildMessage("Olá, " + owner.firstName() + "! " + WELCOME);
	}

	private String flowSimple(Object options) {
		//monta opcao unica e devolve resposta para o usuario
		questionBot.put(owner.id(), ((JSONArray) options).get(0).toString());
		return buildMessage(owner.firstName() + ", " + questionBot.get(owner.id()));
	}

	private String notFound() {
		//informa para o usuario para tentar se comunicar novamente
		questionBot.put(owner.id(), "Desculpe, não entendi sua resposta");
		return buildMessage(questionBot.get(owner.id()));
	}

	private String buildMessage(String message) {
		//caso a mensagem deva guardar alguma informacao
		if(message != null && message.contains("@record")) {
			message = message.replaceAll("@record", answerUser);
			recorded.put(owner.id(), answerUser);
		}
		//o sistema substitui o texto !id pelo id do usuário da conversa
		if(message != null && message.contains("!id")) {
			message = message.replaceAll("!id", owner.id() + "");
		}
		//o sistema verifica se tem alguma gravacao para o código recebido
		if(message != null && message.contains("#find")) {
			String gravacao = recorded.get(Integer.parseInt(userLatestAnswer.get(owner.id())));
			//se houver gravacao, o sistema informa qual o desejo do amigo secreto
			if(gravacao != null)
				message = message.replaceAll("#find", gravacao);
			//caso nao haja gravacao, o sistema notifica o usuário da situacao
			else
				message = "O código informado não corresponde ao desejo de um amigo secreto... por favor tente novamente";
		}

		return message;
	}

}
