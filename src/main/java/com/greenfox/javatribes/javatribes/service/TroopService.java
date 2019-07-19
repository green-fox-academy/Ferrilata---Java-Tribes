package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Troop;

public interface TroopService {

    Troop findById(long id);

    void trainTroop(Kingdom kingdom, Troop troop) throws CustomException;

    void finishTroops();

}
