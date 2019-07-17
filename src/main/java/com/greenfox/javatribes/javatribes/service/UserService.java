package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService{

    Boolean existsByUsername(String username) throws CustomException;

    boolean existsByKingdomName(String name) throws CustomException;

    User findByUsername(String username);

    User findById (long id);

    String login(String username, String password) throws CustomException;

    User identifyUserFromJWTToken(HttpServletRequest httpServletRequest);

    Kingdom identifyUserKingdomFromJWTToken(HttpServletRequest httpServletRequest);

    void register(User user) throws CustomException;

    void updateUser(User user);

}
