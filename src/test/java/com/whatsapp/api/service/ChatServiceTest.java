package com.whatsapp.api.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.matches;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.whatsapp.api.error.RecordNotFoundException;
import com.whatsapp.api.model.Chat;
import com.whatsapp.api.model.Message;
import com.whatsapp.api.model.User;
import com.whatsapp.api.repository.ChatRepository;
import com.whatsapp.api.repository.UsersRepository;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class ChatServiceTest {

    @MockBean
    private UsersRepository userRepositoryMock;

    @MockBean
    private ChatRepository chatRepository;

    @MockBean
    private PushService pushService;

    @Autowired
    private ChatService chatService;

    private Chat mockChat;

    @BeforeEach
    void setup() {
        mockChat = new Chat();
        mockChat.setId(1);
        mockChat.setName("A CHAT NAME");
        mockChat.setUsers(List.of(new User(1, "FIRST USER"), new User(2, "SECOND USER"), new User(3, "THIRD USER")));
        mockChat.setMessages(new ArrayList<>());
    }

    @Test
    void testSend() {
        when(chatRepository.findById(1)).thenReturn(Optional.of(mockChat));
        Message message = chatService.sendMessage(1, 1, "A MESSAGE");
        Assertions.assertEquals("A MESSAGE", message.getMessageString());
        verify(pushService, times(2)).push(eq(1), eq(1), any(), eq("A MESSAGE"));
    }

    @Test
    void testSendShouldThrowOnNonExistentChat() {
        when(chatRepository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(RecordNotFoundException.class, ()-> chatService.sendMessage(1, 1, "A MESSAGE"));
        verifyNoInteractions(pushService);
    }

    @Test
    void testSendShouldThrowOnSenderOutsideChat() {
        when(chatRepository.findById(1)).thenReturn(Optional.of(mockChat));
        Assertions.assertThrows(RecordNotFoundException.class, ()-> chatService.sendMessage(1, 5, "A MESSAGE"));
        verifyNoInteractions(pushService);
    }
}
