package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.User;

public interface UserService{

//    User findByUsernameAndPassword(String username, String password) throws CustomException;

    Boolean existsByUsername(String username) throws CustomException;

//    void saveUser(User user) throws CustomException;

    boolean existsByKingdomName(String name) throws CustomException;

    User findByUsername(String username);

    String login(String username, String password) throws CustomException;

    void register(User user) throws CustomException;

}
