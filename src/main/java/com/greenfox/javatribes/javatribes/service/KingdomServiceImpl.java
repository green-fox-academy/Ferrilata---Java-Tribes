package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Supply;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KingdomServiceImpl implements KingdomService {

    @Override
    public int getGoldAmount(Kingdom kingdom) {

        int goldAmount = 0;
        List<Supply> supplies = kingdom.getSupplies();

        for (Supply supply : supplies) {
            if (supply.getType().equalsIgnoreCase("gold")) {
                goldAmount = supply.getAmount();
            }
        }
        return goldAmount;
    }

    @Override
    public int getFoodAmount(Kingdom kingdom) {

        int foodAmount = 0;
        List<Supply> supplies = kingdom.getSupplies();

        for (Supply supply : supplies) {
            if (supply.getType().equalsIgnoreCase("food")) {
                foodAmount = supply.getAmount();
            }
        }
        return foodAmount;
    }

    @Override
    public void spendGold(Kingdom kingdom, int amount) {

        List<Supply> supplies = kingdom.getSupplies();

        for (Supply supply : supplies) {
            if (supply.getType().equalsIgnoreCase("gold")) {
                supply.setAmount(supply.getAmount() - amount);
            }
        }
    }
}

