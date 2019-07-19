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

import java.util.Optional;

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

        if (kingdom.getGoldAmount() < 250) {
            throw new CustomException("Not enough gold!", HttpStatus.valueOf(400));
        }

        kingdom.spendGold(250);
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

    @Override
    public Building findById(long id) throws CustomException {

        Optional<Building> optionalBuilding = buildingRepository.findById(id);

        if (!optionalBuilding.isPresent()) {
            throw new CustomException("There is no building with this Id!", HttpStatus.valueOf(404));
        }

        return optionalBuilding.get();

    }

    @Override
    public void upgradeBuilding(Building building, int level, long id) {

        Optional<Building> optionalBuilding = buildingRepository.findById(id);

        if (!optionalBuilding.isPresent()) {
            throw new CustomException("There is no building with this Id!", HttpStatus.valueOf(404));
        }

        if (level < 0) {
            throw new CustomException("Invalid building level!", HttpStatus.valueOf(400));
        }

        if (building.getKingdom().getGoldAmount() < (level - building.getLevel()) * 100) {
            throw new CustomException("Not enough gold!", HttpStatus.valueOf(400));
        }

        building.getKingdom().spendGold((level - building.getLevel()) * 100);
        building.setLevel(level);
        buildingRepository.save(building);

    }

}
