package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.EntityNotFoundException;
import com.greenfox.javatribes.javatribes.exceptions.IdentityAlreadyUsedException;
import com.greenfox.javatribes.javatribes.exceptions.UserIdNotFoundException;
import com.greenfox.javatribes.javatribes.model.User;

public interface UserService{

    User findByUsernameAndPassword(String username, String password) throws EntityNotFoundException;

    User findById (long id) throws UserIdNotFoundException;

    User findByUsername (String username) throws UserIdNotFoundException;

    Boolean existsByUsername(String username) throws IdentityAlreadyUsedException;

    void saveUser(User user) throws IdentityAlreadyUsedException;

    void updateUser(User user);

    boolean existsByKingdomName(String name) throws IdentityAlreadyUsedException;

}
