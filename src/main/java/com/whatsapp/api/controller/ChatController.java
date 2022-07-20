package com.whatsapp.api.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "api/chats")
public class ChatController{

    public ResponseEntity<Map<String,String>> postChat(){
        return ResponseEntity.ok().body(Map.of("message", "ok"));
    }
}