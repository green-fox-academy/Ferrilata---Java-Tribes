package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.repositories.KingdomRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class KingdomServiceImpl implements KingdomService {

    private KingdomRepository kingdomRepository;

    public KingdomServiceImpl(KingdomRepository kingdomRepository) {
        this.kingdomRepository = kingdomRepository;
    }


}
