package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.UserIdNotFoundException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.User;
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
    public Kingdom findById(long id) throws UserIdNotFoundException {

        Optional<Kingdom> optionalKingdom = kingdomRepository.findById(id);

        if(!optionalKingdom.isPresent()) {
            throw new UserIdNotFoundException("UserId not found");
        }

        return optionalKingdom.get();

    }

    @Override
    public Kingdom findByUser(User user) throws UserIdNotFoundException {

        Optional<Kingdom> optionalKingdom = kingdomRepository.findByUser(user);

        if(!optionalKingdom.isPresent()) {
            throw new UserIdNotFoundException("UserId not found");
        }

        return optionalKingdom.get();

    }

    @Override
    public void saveKingdom(Kingdom kingdom) {

        kingdomRepository.save(kingdom);

    }

}
