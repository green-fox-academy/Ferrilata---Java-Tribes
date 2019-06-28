package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.repositories.UserRepository;
import javassist.NotFoundException;

import java.util.Optional;

public interface UserService{

    User findByCredentials(String username, String password) throws NotFoundException;
}
