package com.fiap.friendsecret;

import java.io.*;
import java.nio.charset.Charset;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fiap.friendsecret.service.Manager;
import org.springframework.util.ResourceUtils;

/**
 * Responsável por manter a memória de dados do robô
 * @author softon
 *
 */
@Component
public class Memory {

	@Value("${message.default}")
	private String WELCOME;

    /**
     * Carrega o gerenciador de perguntas com dados pré-definidos em um arquivo json
     */
    @SuppressWarnings("unchecked")
	public void loadResponse() {
        try {
            File file = ResourceUtils.getFile("classpath:memory/config.json");

            JSONParser parser = new JSONParser();
            JSONObject data = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));//path to the JSON file.
            Manager.setResponse(data);
            Manager.setDefaultResponse( ((JSONArray) data.get(WELCOME)).get(0).toString());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
