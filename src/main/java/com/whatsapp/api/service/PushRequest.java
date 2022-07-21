package com.whatsapp.api.service;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PushRequest {
    private Integer senderId;
    private Integer chatId;
    private Integer recipientId;
    private String message;
}   
