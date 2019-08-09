package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.model.Building;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Supply;
import com.greenfox.javatribes.javatribes.repositories.KingdomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KingdomServiceImpl implements KingdomService {

    private KingdomRepository kingdomRepository;

    public KingdomServiceImpl(KingdomRepository kingdomRepository) {
        this.kingdomRepository = kingdomRepository;
    }


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
    public boolean hasBarracks(Kingdom kingdom) {

        List<Building> buildings = kingdom.getBuildings();

        for (Building building : buildings) {

            if (building.getType().equalsIgnoreCase("barracks")) {
                return true;
            }
        }
        return false;
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

