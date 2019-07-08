package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.KingdomNotFoundException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.repositories.KingdomRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KingdomServiceImpl implements KingdomService {

    private KingdomRepository kingdomRepository;

    public KingdomServiceImpl(KingdomRepository kingdomRepository) {
        this.kingdomRepository = kingdomRepository;
    }


    @Override
    public Kingdom findById(Long Id) throws KingdomNotFoundException {

        Optional<Kingdom> optionalKingdom = kingdomRepository.findById(Id);

        if (!optionalKingdom.isPresent()){
            throw new KingdomNotFoundException("There is no kingdom with this Id. Please enter a different Id.");
        }

        return optionalKingdom.get();

    }

    @Override
    public void saveKingdom(Kingdom kingdom) {

        kingdomRepository.save(kingdom);

    }
}
