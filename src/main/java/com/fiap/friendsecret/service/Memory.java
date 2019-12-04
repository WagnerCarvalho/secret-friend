package com.fiap.friendsecret.service;

import com.fiap.friendsecret.entities.Manager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.FileReader;
import java.io.IOException;

@SpringBootApplication
public class Memory {

    private Manager manager = new Manager();

    public void loadResponse() {
        try {
            JSONParser parser = new JSONParser();
            JSONObject data = (JSONObject) parser.parse(new FileReader("C://Users//logonrmlocal//git//secret-friend-wagner//src//main//resources//memory//config.json"));//path to the JSON file.
            manager.setResponse(data);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
