package com.fiap.friendsecret;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fiap.friendsecret.service.Manager;

/**
 * Responsável por manter a memória de dados do robô
 * @author softon
 *
 */
@Component
public class Memory {

    @Value("${telegram.file.config}")
    private String configFile;

	@Value("${telegram.welcome}") 
	private String WELCOME;
	
    /**
     * Carrega o gerenciador de perguntas com dados pré-definidos
     */
    @SuppressWarnings("unchecked")
	public void loadResponse() {
        try {
            JSONParser parser = new JSONParser();
            JSONObject data = (JSONObject) parser.parse(new FileReader(configFile));//path to the JSON file.
            Manager.setResponse(data);
            Manager.setDefaultResponse(WELCOME);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
