package com.fiap.friendsecret;

import com.fiap.friendsecret.service.Bot;
import com.fiap.friendsecret.service.Memory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FriendSecretApplication {

	private static Bot bot;

	public FriendSecretApplication(Bot bot) { this.bot = bot; }

	public static void main(String[] args) {
		Memory memory = new Memory();
		memory.loadResponse();

		SpringApplication.run(FriendSecretApplication.class, args);
		bot.startBot();
	}

}
