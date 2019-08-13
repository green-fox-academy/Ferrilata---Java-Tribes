package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.dto.RegisterObject;
import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    Boolean existsByUsername(String username) throws CustomException;

    boolean existsByKingdomName(String name) throws CustomException;

    User findByUsername(String username);

    User findById(long id);

    String login(String username, String password) throws CustomException;

    User getUserFromToken(HttpServletRequest httpServletRequest);

    User register(RegisterObject registerObject) throws CustomException;

    void saveUser(User user);

    Kingdom updateKingdom(Kingdom kingdom, String name);
}
