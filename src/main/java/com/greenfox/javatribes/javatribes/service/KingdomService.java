package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.UsernameAlreadyUsedException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import org.springframework.stereotype.Service;

public interface KingdomService {

    void saveKingdom(Kingdom kingdom);
}
