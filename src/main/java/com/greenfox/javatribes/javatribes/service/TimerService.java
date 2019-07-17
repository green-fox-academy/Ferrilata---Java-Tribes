package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.model.Troop;
import com.greenfox.javatribes.javatribes.repositories.TroopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Timer;
import java.util.TimerTask;

@Configuration
@EnableScheduling
@Service
public class TimerService {

    @Autowired
    UserService userService;
    @Autowired
    SupplyService supplyService;
    @Autowired
    TroopRepository troopRepository;

    @Scheduled(fixedRate = 1000)
    public void scheduleFixedRateTask() {

        supplyService.earnAll();

    }


    public void finishTroop(Troop troop) {

        TimerTask task = new TimerTask() {

            public void run() {

                troop.setReady(true);
                troopRepository.save(troop);

            }
        };

            Timer timer = new Timer("Timer");
            Long delay = 100L;
            timer.schedule(task, delay);

    }

}