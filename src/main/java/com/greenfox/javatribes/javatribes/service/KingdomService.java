package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.model.Kingdom;

public interface KingdomService {

    int getGoldAmount(Kingdom kingdom);

    int getFoodAmount(Kingdom kingdom);

    int getStorage(Kingdom kingdom);

    boolean hasBarracks(Kingdom kingdom);

    boolean hasTownhall(Kingdom kingdom);

    void spendGold(Kingdom kingdom, int amount);

    int getTownhallLevel(Kingdom kingdom);

}
