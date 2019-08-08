package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Troop;
import com.greenfox.javatribes.javatribes.repositories.KingdomRepository;
import com.greenfox.javatribes.javatribes.repositories.TroopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TroopServiceImpl implements TroopService {

    @Autowired
    KingdomRepository kingdomRepository;
    @Autowired
    TroopRepository troopRepository;
    @Autowired
    KingdomService kingdomService;

    @Override
    public Troop findById(long id) throws CustomException {

        Optional<Troop> optionalTroop = troopRepository.findById(id);

        if (!optionalTroop.isPresent()) {
            throw new CustomException("There is no troop with this Id!", HttpStatus.valueOf(404));
        }
        return optionalTroop.get();
    }

    @Override
    public Troop findByIdAndKingdom(long id, Kingdom kingdom) throws CustomException {

        Optional<Troop> optionalTroop = troopRepository.findByIdAndKingdom(id, kingdom);

        if (!optionalTroop.isPresent()) {
            throw new CustomException("There is no troop with this Id!", HttpStatus.valueOf(404));
        }
        return optionalTroop.get();
    }

    @Override
    public Troop upgradeTroop(Kingdom kingdom, int level, long id) {

        Troop troopToUpgrade = this.findByIdAndKingdom(id, kingdom);

        if (level < 0) {
            throw new CustomException("Invalid troop level!", HttpStatus.valueOf(400));
        }

        if (kingdomService.getGoldAmount(kingdom) < (level - troopToUpgrade.getLevel()) * 5) {
            throw new CustomException("Not enough gold!", HttpStatus.valueOf(400));
        }

        kingdomService.spendGold(kingdom, (level - troopToUpgrade.getLevel()) * 5);
        troopToUpgrade.setLevel(level);
        return troopRepository.save(troopToUpgrade);

    }

    @Override
    public Troop trainTroop(Kingdom kingdom) throws CustomException {

        Troop troop = new Troop(kingdom);
        if (kingdomService.getGoldAmount(kingdom) < 10) {
            throw new CustomException("Not enough gold!", HttpStatus.valueOf(400));
        }

        kingdomService.spendGold(kingdom, 10);
        kingdom.addTroop(troop);
        kingdomRepository.save(kingdom);
        return troop;
    }
}
