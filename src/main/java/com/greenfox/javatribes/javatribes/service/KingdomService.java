package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.EntityNotFoundException;
import com.greenfox.javatribes.javatribes.model.Kingdom;

public interface KingdomService {

    Kingdom findById(Long Id) throws EntityNotFoundException;

    void saveKingdom(Kingdom kingdom);

}
