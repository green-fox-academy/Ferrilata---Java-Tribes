package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.model.Supply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class TimeService {

    @Autowired
    UserService userService;
    @Autowired
    SupplyService supplyService;

    Supply supply = new Supply("gold",5,5);

    @Scheduled(fixedRate = 1000)
    public void scheduleFixedRateTask() {

        supplyService.earnById(1);
        supply.setAmount(supply.getAmount()+supply.getGeneration());

    }



}
