package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.repositories.TroopRepository;
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
    UserService userService;
    @Autowired
    SupplyService supplyService;
    @Autowired
    TroopService troopService;
    @Autowired
    TroopRepository troopRepository;

    @Scheduled(fixedRate = 10000)
    public void scheduleFixedRateResourceEarning() {

        supplyService.earnAll();

    }

    @Scheduled(fixedRate = 10000)
    public void scheduledFixedRateTroopFinish() {

        troopService.finishTroops();

    }

    /*public void finishTroop(Kingdom kingdom, Troop troop) {

        TimerTask task = new TimerTask() {

            public void run() {

                troop.setReady(true);
                troopRepository.save(troop);

            }
        };

            Timer timer = new Timer("Timer");
            Long delay = 100L;
            timer.schedule(task, delay);

    }*/

}