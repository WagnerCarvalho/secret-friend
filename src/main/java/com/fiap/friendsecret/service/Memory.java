package com.fiap.friendsecret.service;

import com.fiap.friendsecret.entities.Manager;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class Memory {

    private Manager manager = new Manager();

    public void loadResponse() {
        Map<String, String> response = new HashMap<>();
        response.put("SIM", "Informe qual seu apelido ?");
        response.put("NÃO", "Em que posso te ajudar ?");
        manager.setResponse(response);
    }
}
