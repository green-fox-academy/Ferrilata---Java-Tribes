package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.User;

public interface UserService{

    Boolean existsByUsername(String username) throws CustomException;

    boolean existsByKingdomName(String name) throws CustomException;

    User findByUsername(String username);

    String login(String username, String password) throws CustomException;

    void register(User user) throws CustomException;

}
