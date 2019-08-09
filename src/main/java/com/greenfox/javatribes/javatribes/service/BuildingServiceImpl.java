package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Building;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Supply;
import com.greenfox.javatribes.javatribes.model.Troop;
import com.greenfox.javatribes.javatribes.repositories.BuildingRepository;
import com.greenfox.javatribes.javatribes.repositories.KingdomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private KingdomRepository kingdomRepository;
    @Autowired
    private KingdomService kingdomService;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    TimerService timerService;
    @Autowired
    TroopService troopService;

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

        if (building.getType().equalsIgnoreCase("townhall") &&
            kingdomService.hasTownhall(building.getKingdom())) {

            throw new CustomException("A kingdom can have only one townhall!", HttpStatus.valueOf(400));

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

        /*int finishedBuildings = (int) supply.getKingdom().getBuildings().stream()
                .filter(building -> building.getType().equalsIgnoreCase(type))
                .filter(building -> timerService.finishedBuilding(building))
                .count();*/

        List<Building> finishedBuildings = supply.getKingdom().getBuildings().stream()
                .filter(building -> building.getType().equalsIgnoreCase(type))
                .filter(building -> timerService.finishedBuilding(building))
                .collect(Collectors.toList());

        int supplyGenerationCoeficient = 0;

        for (Building building : finishedBuildings) {

            supplyGenerationCoeficient = supplyGenerationCoeficient + building.getLevel();

        }

        return supplyGenerationCoeficient;

    }

    @Override
    public void upgradeBuilding(Building building, int level, long id) {

        //Optional<Building> optionalBuilding = buildingRepository.findById(id);

        /*if (!optionalBuilding.isPresent()) {
            throw new CustomException("There is no building with this Id!", HttpStatus.valueOf(404));
        }*/

        if (level <= building.getLevel()) {
            throw new CustomException("Invalid building level!", HttpStatus.valueOf(400));
        }

        if (level > 20) {
            throw new CustomException("Buildings can not have level higher than 20!", HttpStatus.valueOf(400));
        }

        if (!building.getType().equalsIgnoreCase("townhall") &&
                kingdomService.getTownhallLevel(building.getKingdom()) < level) {
            throw new CustomException("In order to upgrade a building to a certain level, the kingdom must have a townhall of the same or higher level!", HttpStatus.valueOf(400));
        }

        if (kingdomService.getGoldAmount(building.getKingdom()) < ((level - building.getLevel()) * 100)) {
            throw new CustomException("Not enough gold!", HttpStatus.valueOf(400));
        }

        if (building.getType().equalsIgnoreCase("barracks")) {

            Iterable<Troop> troops = troopService.findAll();
            troops.forEach(troop -> troop.setHp(level*10));
            troopService.saveAll(troops);

        }

        kingdomService.spendGold(building.getKingdom(), (level - building.getLevel()) * 100);
        building.setLevel(level);
        buildingRepository.save(building);

    }

}
