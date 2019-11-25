package com.fiap.friendsecret;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;

@SpringBootApplication
@EnableScheduling
public class FriendSecretApplication {

    @Value("${telegram.token}")
    private String TOKEN;
	
	public static void main(String[] args) {
		SpringApplication.run(FriendSecretApplication.class, args);
	}
	
	@Bean
	public TelegramBot getTelegramBot() {
		TelegramBot telegramBot = TelegramBotAdapter.build(TOKEN);
		
		return telegramBot;
	}
}
