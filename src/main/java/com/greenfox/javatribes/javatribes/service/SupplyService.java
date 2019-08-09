package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Supply;

public interface SupplyService {

    Supply findById(long id);

    void earnAmount(Supply supply);

    void earnAll();

    Supply findByKingdomAndType(Kingdom kingdom, String type);

    void generationRecalculator(Supply supply);

}
