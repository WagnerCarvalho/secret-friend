package com.fiap.friendsecret;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;

/**
 * Classe responsável pela inicialização da aplicação e agendamento da leitura de mensagens pelo bot @Multitarefa_bot
 */
@SpringBootApplication
@EnableScheduling
public class FriendSecretApplication {

    @Value("${telegram.token}")
    private String TOKEN;

	/**
	 * Inicia a aplicação
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(FriendSecretApplication.class, args);
	}

	/**
	 * Provê o programa central do bot para o telegram inicializado com o token encontrado no arquivo de propriedades (telegram.token)
	 * @return
	 */
	@Bean
	public TelegramBot getTelegramBot() {
		TelegramBot telegramBot = TelegramBotAdapter.build(TOKEN);
		
		return telegramBot;
	}
}
