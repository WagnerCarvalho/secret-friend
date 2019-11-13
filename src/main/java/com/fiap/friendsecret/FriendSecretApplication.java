package com.fiap.friendsecret;

import com.fiap.friendsecret.service.Bot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FriendSecretApplication {

	private static Bot bot;

	public FriendSecretApplication(Bot bot) { this.bot = bot; }

	public static void main(String[] args) {
		SpringApplication.run(FriendSecretApplication.class, args);
		bot.startBot();
	}

}
