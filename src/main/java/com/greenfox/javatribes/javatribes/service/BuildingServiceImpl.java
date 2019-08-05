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
    public Building constructBuilding(Kingdom kingdom, String buildingType) throws CustomException {

        if (buildingType.equalsIgnoreCase("")) {
            throw new CustomException("Missing parameter(s): type!", HttpStatus.valueOf(400));
        }

        if (!(buildingType.equalsIgnoreCase("mine") ||
                buildingType.equalsIgnoreCase("farm") ||
                buildingType.equalsIgnoreCase("townhall") ||
                buildingType.equalsIgnoreCase("barracks"))) {

            throw new CustomException("Invalid building type!", HttpStatus.valueOf(400));
        }

        if (kingdomService.getGoldAmount(kingdom) < 250) {
            throw new CustomException("Not enough gold!", HttpStatus.valueOf(400));
        }
        Building newBuilding = new Building(buildingType);
        kingdom.addBuilding(newBuilding);
        kingdomService.spendGold(kingdom, 250);
        kingdomRepository.save(kingdom);

        return newBuilding;
    }

    @Override
    public Building findByIdAndKingdom(long id, Kingdom kingdom) throws CustomException {

        Optional<Building> optionalBuilding = buildingRepository.findByIdAndKingdom(id, kingdom);

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
    public Building upgradeBuilding(Kingdom kingdom, int level, long id) {

        Building building = findByIdAndKingdom(id, kingdom);

        if (level < 0) {
            throw new CustomException("Invalid building level!", HttpStatus.valueOf(400));
        }

        if (kingdomService.getGoldAmount(kingdom) < (level - building.getLevel()) * 100) {
            throw new CustomException("Not enough gold!", HttpStatus.valueOf(400));
        }
        kingdomService.spendGold(kingdom, (level - building.getLevel()) * 100);
        building.setLevel(level);
        buildingRepository.save(building);

        return  building;
    }
}
