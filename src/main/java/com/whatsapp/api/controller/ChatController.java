package com.whatsapp.api.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.whatsapp.api.controller.view.CreateChatView;
import com.whatsapp.api.controller.view.GenericMessageView;
import com.whatsapp.api.controller.view.SendMessageView;
import com.whatsapp.api.model.Chat;
import com.whatsapp.api.model.Message;
import com.whatsapp.api.service.ChatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(path = "api/chats")
public class ChatController {

    private ChatService service;

    @Autowired
    public void setChatService(ChatService service) {
        this.service = service;
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Chat> createChat(@RequestBody CreateChatView chat) {
        if (!chat.getUserIds().isEmpty()) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(service.createChat(chat.getChatName(), chat.getUserIds()));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "{chatId}/messages", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Message>> getMessages(@PathVariable Integer chatId,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) Date since) {
        return ResponseEntity.ok(service.getMessages(chatId, since));
    }

    @DeleteMapping(path = "{chatId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<GenericMessageView> deleteChat(@PathVariable Integer chatId) {
        service.deleteChat(chatId);
        return ResponseEntity.ok(new GenericMessageView("Deleted"));
    }

    @PostMapping(path = "{chatId}/messages", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Message> getMessages(@PathVariable Integer chatId, @RequestBody SendMessageView messageView) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.sendMessage(chatId, messageView.getSenderId(), messageView.getMessage()));
    }

}