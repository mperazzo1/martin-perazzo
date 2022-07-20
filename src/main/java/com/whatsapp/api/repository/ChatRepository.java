package com.whatsapp.api.repository;

import java.util.List;

import com.whatsapp.api.model.Chat;
import com.whatsapp.api.model.Message;

import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<Chat, Integer>{
    Chat findChatById(Integer id);

    //List<Message> findMessagesByChatId(Integer id);
}
