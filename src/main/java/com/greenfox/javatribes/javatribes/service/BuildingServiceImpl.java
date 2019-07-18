package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Building;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.repositories.BuildingRepository;
import com.greenfox.javatribes.javatribes.repositories.KingdomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private KingdomRepository kingdomRepository;
    @Autowired
    private BuildingRepository buildingRepository;

    @Override
    public void constructBuilding(Kingdom kingdom, Building building) throws CustomException {

        if (building.getType().equalsIgnoreCase("")) {
            throw new CustomException("Missing parameter(s): type!", HttpStatus.valueOf(400));
        }

        if (!(building.getType().equalsIgnoreCase("mine") ||
                building.getType().equalsIgnoreCase("farm") ||
                building.getType().equalsIgnoreCase("townhall") ||
                building.getType().equalsIgnoreCase("barracks"))) {

            throw new CustomException("Invalid building type!", HttpStatus.valueOf(400));
        }

        if (kingdom.getGoldAmount() < 5) {
            throw new CustomException("Not enough gold!", HttpStatus.valueOf(400));
        }

        kingdom.spendGold(5);
        kingdom.addBuilding(building);
        kingdom.getSupplies().forEach(supply -> supply.generationRecalculator());
        kingdomRepository.save(kingdom);

    }

    @Override
    @Transactional
    public void finishBuildings() {

        buildingRepository.findAll().forEach(building -> building.finishProduction());
        buildingRepository.findAll().forEach(building -> buildingRepository.save(building));

    }

}
