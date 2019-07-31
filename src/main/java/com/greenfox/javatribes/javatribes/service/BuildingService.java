package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Building;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Supply;

public interface BuildingService {

    Building findById(long id);

    int finishedBuildingCalculator (Supply supply, String type);

    void upgradeBuilding(Building building, int level, long id);

    void constructBuilding(Kingdom kingdom, Building building) throws CustomException;

    //void finishBuildings();

}
