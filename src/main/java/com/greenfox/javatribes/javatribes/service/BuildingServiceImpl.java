package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Building;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Supply;
import com.greenfox.javatribes.javatribes.repositories.BuildingRepository;
import com.greenfox.javatribes.javatribes.repositories.KingdomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    KingdomRepository kingdomRepository;
    @Autowired
    KingdomService kingdomService;
    @Autowired
    BuildingRepository buildingRepository;
    @Autowired
    TimerService timerService;

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

        if (kingdomService.getGoldAmount(kingdom) < 250) {
            throw new CustomException("Not enough gold!", HttpStatus.valueOf(400));
        }

        kingdomService.spendGold(kingdom, 250);
        kingdom.addBuilding(building);
        kingdomRepository.save(kingdom);

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
    public int finishedBuildingCalculator(Supply supply, String type) {

        return (int) supply.getKingdom().getBuildings().stream()
                .filter(building -> building.getType().equalsIgnoreCase(type))
                .filter(timerService::finishedBuilding)
                .count();

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

        if (kingdomService.getGoldAmount(building.getKingdom()) < (level - building.getLevel()) * 100) {
            throw new CustomException("Not enough gold!", HttpStatus.valueOf(400));
        }

        kingdomService.spendGold(building.getKingdom(), (level - building.getLevel()) * 100);
        building.setLevel(level);
        buildingRepository.save(building);

    }

}
