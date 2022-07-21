package com.whatsapp.api.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.whatsapp.api.error.RecordNotFoundException;
import com.whatsapp.api.model.Chat;
import com.whatsapp.api.model.Message;
import com.whatsapp.api.model.User;
import com.whatsapp.api.repository.ChatRepository;
import com.whatsapp.api.repository.UsersRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
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

    private List<User> mockUsers = List.of(new User(1, "FIRST USER"), new User(2, "SECOND USER"),
            new User(3, "THIRD USER"));

    @Nested
    class SendMessage {

        private Chat mockChat;

        @BeforeEach
        void setup() {
            mockChat = new Chat();
            mockChat.setId(1);
            mockChat.setName("A CHAT NAME");
            mockChat.setUsers(
                    mockUsers);
            mockChat.setMessages(new ArrayList<>());
        }

        @Test
        void test() {
            when(chatRepository.findById(1)).thenReturn(Optional.of(mockChat));
            Message message = chatService.sendMessage(1, 1, "A MESSAGE");
            Assertions.assertEquals("A MESSAGE", message.getMessageString());
            verify(pushService, times(2)).push(eq(1), eq(1), any(), eq("A MESSAGE"));
        }

        @Test
        void testShouldThrowOnNonExistentChat() {
            when(chatRepository.findById(1)).thenReturn(Optional.empty());
            Assertions.assertThrows(RecordNotFoundException.class, () -> chatService.sendMessage(1, 1, "A MESSAGE"));
            verifyNoInteractions(pushService);
        }

        @Test
        void testShouldThrowOnSenderOutsideChat() {
            when(chatRepository.findById(1)).thenReturn(Optional.of(mockChat));
            Assertions.assertThrows(RecordNotFoundException.class, () -> chatService.sendMessage(1, 5, "A MESSAGE"));
            verifyNoInteractions(pushService);

        }
    }

    @Nested
    class CreateChat {
        @BeforeEach
        void setup() {
            when(userRepositoryMock.findAllById(anyIterable())).thenReturn(mockUsers);
            when(chatRepository.save(any())).then(new Answer<Chat>() {
                @Override
                public Chat answer(InvocationOnMock invocation) throws Throwable {
                    return invocation.getArgument(0, Chat.class);
                }
            });
        }

        @Test
        void test() {
            Chat chat = chatService.createChat("A_CHAT_NAME", List.of(1, 2, 3));
            Assertions.assertEquals("A_CHAT_NAME", chat.getName());
            Assertions.assertIterableEquals(mockUsers, chat.getUsers());
        }

        @Test
        void testShouldThrowOnNonExistentUser() {
            Assertions.assertThrows(RecordNotFoundException.class,
                    () -> chatService.createChat("A_CHAT_NAME", List.of(1, 2, 3, 4, 5)));
            verifyNoInteractions(chatRepository);
        }

    }
}
