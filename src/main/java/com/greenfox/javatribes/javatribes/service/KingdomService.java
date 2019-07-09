package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.UserIdNotFoundException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.User;

public interface KingdomService {

    Kingdom findById (long id) throws UserIdNotFoundException;

    Kingdom findByUser (User user) throws UserIdNotFoundException;

    void saveKingdom(Kingdom kingdom);

}
