package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Building;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Supply;

public interface BuildingService {

    Building findByIdAndKingdom(long id, Kingdom kingdom) throws CustomException;

    int finishedBuildingCalculator(Supply supply, String type);

    Building upgradeBuilding(Kingdom kingdom, int level, long id);

    Building constructBuilding(Kingdom kingdom, String buildingType) throws CustomException;

}
