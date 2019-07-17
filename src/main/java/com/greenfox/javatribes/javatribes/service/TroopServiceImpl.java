package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Troop;
import com.greenfox.javatribes.javatribes.repositories.KingdomRepository;
import com.greenfox.javatribes.javatribes.repositories.TroopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TroopServiceImpl implements TroopService {

    @Autowired
    private KingdomRepository kingdomRepository;

    @Autowired
    TimerService timerservice;

    @Autowired
    private TroopRepository troopRepository;

    @Override
    public void trainTroop(Kingdom kingdom, Troop troop) throws CustomException {

        if (kingdom.getGoldAmount() < 10) {
            throw new CustomException("Not enough gold!", HttpStatus.valueOf(400));
        }

        kingdom.spendGold(10);

        kingdom.addTroop(troop);
        long id = troop.getId();
        timerservice.finishTroop(troop);
        kingdomRepository.save(kingdom);

    }
}
