package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Building;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.repositories.KingdomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private KingdomRepository kingdomRepository;

    @Override
    public void constructBuilding(Kingdom kingdom, Building building) throws CustomException {

        if     (building.getType().equalsIgnoreCase("")) {
            throw new CustomException("Missing parameter(s): type!", HttpStatus.valueOf(400));
        }

        if     (!(building.getType().equalsIgnoreCase("mine") ||
                  building.getType().equalsIgnoreCase("farm") ||
                  building.getType().equalsIgnoreCase("townhall") ||
                  building.getType().equalsIgnoreCase("barracks") )) {

            throw new CustomException("Invalid building type!", HttpStatus.valueOf(400));
        }

        if (kingdom.getGoldAmount() < 5) {
            throw new CustomException("Not enough gold!", HttpStatus.valueOf(400));
        }

        kingdom.spendGold(5);
        kingdom.addBuilding(building);
        kingdomRepository.save(kingdom);

    }

}
