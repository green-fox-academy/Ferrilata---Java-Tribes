package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.EntityNotFoundException;
import com.greenfox.javatribes.javatribes.exceptions.UsernameAlreadyUsedException;
import com.greenfox.javatribes.javatribes.model.User;

public interface UserService{

    User findByUsernameAndPassword(String username, String password) throws EntityNotFoundException;

    Boolean existsByUsername(String username) throws UsernameAlreadyUsedException;

    void saveUser(User user) throws UsernameAlreadyUsedException;

}
