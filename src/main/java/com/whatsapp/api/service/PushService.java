package com.whatsapp.api.service;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import com.whatsapp.api.configuration.PushConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PushService {
    @Autowired
    private PushConfiguration configuration;
    private RestTemplate restTemplate;

    @PostConstruct
    void postConstruct() {
        restTemplate = new RestTemplateBuilder().build();
    }

    public void push(int chatId, int sender, int recipient, String message) {
        restTemplate.exchange(String.format("%s/%s", configuration.getServer(), configuration.getPath()), HttpMethod.POST,
                new HttpEntity<>(new PushRequest(chatId, sender, recipient, message)), HashMap.class);
    }
}
