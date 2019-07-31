package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.model.Kingdom;

public interface KingdomService {

    int getGoldAmount(Kingdom kingdom);

    int getFoodAmount(Kingdom kingdom);

    void spendGold(Kingdom kingdom, int amount);

}
