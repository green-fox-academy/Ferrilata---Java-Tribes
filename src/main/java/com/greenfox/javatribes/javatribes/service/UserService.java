package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.model.User;

public interface UserService {

    User findUserByUsername(String username);
}
