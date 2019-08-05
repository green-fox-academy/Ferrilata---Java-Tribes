package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Troop;

public interface TroopService {

    Troop findById(long id);

    Troop findByIdAndKingdom(long id, Kingdom kingdom);

    Troop upgradeTroop(Kingdom kingdom, int level, long id);

    Troop trainTroop(Kingdom kingdom) throws CustomException;

}
