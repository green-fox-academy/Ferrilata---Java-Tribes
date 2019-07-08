package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.KingdomNotFoundException;
import com.greenfox.javatribes.javatribes.model.Kingdom;

public interface KingdomService {

    Kingdom findById(Long Id) throws KingdomNotFoundException;

    void saveKingdom(Kingdom kingdom);

}
