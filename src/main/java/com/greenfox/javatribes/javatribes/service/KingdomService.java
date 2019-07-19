package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Supply;

public interface KingdomService {

    Supply getSupplyByType(String type, Kingdom kingdom);

}
