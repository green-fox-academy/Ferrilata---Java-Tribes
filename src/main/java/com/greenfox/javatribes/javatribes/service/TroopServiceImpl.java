package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Troop;
import com.greenfox.javatribes.javatribes.repositories.KingdomRepository;
import com.greenfox.javatribes.javatribes.repositories.TroopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TroopServiceImpl implements TroopService {

    @Autowired
    private KingdomRepository kingdomRepository;

    @Autowired
    private TroopRepository troopRepository;

    @Override
    public void trainTroop(Kingdom kingdom, Troop troop) throws CustomException {

        if (kingdom.getGoldAmount() < 10) {
            throw new CustomException("Not enough gold!", HttpStatus.valueOf(400));
        }

        kingdom.spendGold(10);
        kingdom.addTroop(troop);
        kingdomRepository.save(kingdom);

    }

    @Override
    @Transactional
    public void finishTroops() {

        troopRepository.findAll().forEach(troop -> troop.finishProduction());
        troopRepository.findAll().forEach(troop -> troopRepository.save(troop));

    }

}
