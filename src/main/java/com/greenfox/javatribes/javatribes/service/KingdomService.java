package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.UserIdNotFoundException;
import com.greenfox.javatribes.javatribes.model.Kingdom;

public interface KingdomService {

    Kingdom findById (long Id) throws UserIdNotFoundException;

    void saveKingdom(Kingdom kingdom);

}
