package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import org.springframework.http.HttpStatus;

public class TroopServiceImpl implements TroopService {

    @Override
    public void trainTroop(Kingdom kingdom) throws CustomException {

        if (kingdom.getGoldAmount() < 20) {
            throw new CustomException("Not enough gold!", HttpStatus.valueOf(400));
        }

        kingdom.spendGold(20);

    }
}
