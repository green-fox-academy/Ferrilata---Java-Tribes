package com.greenfox.javatribes.javatribes.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SupplyTimerService {

    @Autowired
    UserService userService;
    @Autowired
    SupplyService supplyService;

    @Scheduled(fixedRate = 1000)
    public void scheduleFixedRateTask() {


        supplyService.earnById(1);


    }



}