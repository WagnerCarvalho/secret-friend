package com.fiap.friendsecret;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.json.simple.JSONArray;
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

    @Value("${memory.file}")
    private String configFile;

	@Value("${message.default}")
	private String WELCOME;

    /**
     * Carrega o gerenciador de perguntas com dados pré-definidos
     */
    @SuppressWarnings("unchecked")
	public void loadResponse() {
        try {
            JSONParser parser = new JSONParser();
            JSONObject data = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream(configFile), Charset.forName("UTF-8")));//path to the JSON file.
            Manager.setResponse(data);
            Manager.setDefaultResponse( ((JSONArray) data.get(WELCOME)).get(0).toString());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
