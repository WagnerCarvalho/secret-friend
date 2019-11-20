package com.fiap.friendsecret;

import com.fiap.friendsecret.service.Bot;
import com.fiap.friendsecret.service.Memory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FriendSecretApplication {

	private static Bot bot;
	private static Memory memory;

	public FriendSecretApplication(Bot bot, Memory memory) {
		this.bot = bot;
		this.memory = memory;
	}

	public static void main(String[] args) {
		SpringApplication.run(FriendSecretApplication.class, args);
		memory.loadResponse();
		bot.startBot();
	}

}
