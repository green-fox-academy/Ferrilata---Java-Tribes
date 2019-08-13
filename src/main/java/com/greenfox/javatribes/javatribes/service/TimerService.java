package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.model.Building;
import com.greenfox.javatribes.javatribes.model.Troop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Configuration
@EnableScheduling
@Service
public class TimerService {

    @Autowired
    SupplyService supplyService;

    @Scheduled(fixedRate = 10000)
    public void scheduleFixedRateResourceEarning() {

        supplyService.earnAll();
    }

    public boolean finishedTroop(Troop troop) {

        return System.currentTimeMillis() > troop.getFinishedAt().getTime();
    }

    public boolean finishedBuilding(Building building) {

        return System.currentTimeMillis() > building.getFinishedAt().getTime();
    }

}