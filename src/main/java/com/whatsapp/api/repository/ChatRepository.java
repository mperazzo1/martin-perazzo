package com.whatsapp.api.repository;

import java.util.Date;
import java.util.List;

import com.whatsapp.api.model.Chat;
import com.whatsapp.api.model.Message;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<Chat, Integer>{
    Chat findChatById(Integer id);

    @Query("SELECT c.messages FROM Chat c WHERE c.id = ?1")
    List<Message> findMessagesByChatId(Integer id);

    @Query("SELECT m FROM Chat c JOIN c.messages m WHERE c.id = ?1 AND m.timestamp > ?2")
    List<Message> findMessagesByChatIdSinceDate(Integer id, Date date);
}
