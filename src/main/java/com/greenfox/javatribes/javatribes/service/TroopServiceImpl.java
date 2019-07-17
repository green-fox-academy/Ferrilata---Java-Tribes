package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Troop;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TroopServiceImpl implements TroopService {

    @Override
    public void trainTroop(Kingdom kingdom, Troop troop) throws CustomException {

        if (kingdom.getGoldAmount() < 10) {
            throw new CustomException("Not enough gold!", HttpStatus.valueOf(404));
        }

        kingdom.spendGold(10);

    }
}
