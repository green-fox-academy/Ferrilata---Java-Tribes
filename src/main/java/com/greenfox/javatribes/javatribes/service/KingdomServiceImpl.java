package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.repositories.KingdomRepository;
import org.springframework.stereotype.Service;

@Service
public class KingdomServiceImpl implements KingdomService {

    private KingdomRepository kingdomRepository;

    public KingdomServiceImpl(KingdomRepository kingdomRepository) {
        this.kingdomRepository = kingdomRepository;
    }


}
