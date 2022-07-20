package com.whatsapp.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whatsapp.api.model.Chat;
import com.whatsapp.api.model.Message;
import com.whatsapp.api.model.User;
import com.whatsapp.api.repository.ChatRepository;
import com.whatsapp.api.repository.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatService {
    private UsersRepository userRepository;

    private ChatRepository chatRepository;

    @Autowired
    public void setUsersRepository(UsersRepository repository){
        userRepository= repository;
    }

    @Autowired
    public void setChatRepository(ChatRepository repository){
        chatRepository=repository;
    }

    public Chat createChat(String chatName, List<Integer> userIds){
        Chat newChat= new Chat();
        newChat.setName(chatName);
        List<User> users= new ArrayList<>();
        userRepository.findAllById(userIds).forEach(users::add);
        newChat.setUsers( users);
        return chatRepository.save(newChat);
    }

    public List<Message> getMessages(Integer chatId, Date since) {
        return since==null
        ? this.chatRepository.findMessagesByChatId(chatId)
        : this.chatRepository.findMessagesByChatIdSinceDate(chatId, since);
    }

    @Transactional
    public Message sendMessage(Integer chatId, Integer senderId, String message) {
        Message newMessage = new Message();
        Chat chat = this.chatRepository.findById(chatId).orElseThrow();
        User sender = this.userRepository.findById(senderId).orElseThrow();
        newMessage.setMessageString(message);
        newMessage.setSender(sender);
        chat.getMessages().add(newMessage);
        return newMessage;
    }
}
