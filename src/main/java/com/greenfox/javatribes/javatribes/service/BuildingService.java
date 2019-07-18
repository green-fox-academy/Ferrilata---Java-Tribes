package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Building;
import com.greenfox.javatribes.javatribes.model.Kingdom;

public interface BuildingService {

    void constructBuilding(Kingdom kingdom, Building building) throws CustomException;

    void finishBuildings();

}
