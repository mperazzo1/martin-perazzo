package com.whatsapp.api.repository;

import com.whatsapp.api.model.User;

import org.springframework.data.repository.CrudRepository;

public interface UsersRepository  extends CrudRepository<User, Integer>{
    
}
