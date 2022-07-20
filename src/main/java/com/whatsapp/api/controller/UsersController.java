package com.whatsapp.api.controller;

import com.whatsapp.api.model.User;
import com.whatsapp.api.repository.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(path = "api/users")
public class UsersController {

    private UsersRepository repository;

    @Autowired
    public void setUsersRepository(UsersRepository repository){
        this.repository= repository;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> postUser(@RequestBody User user){
        return ResponseEntity.ok(repository.save(user));
    }
}
