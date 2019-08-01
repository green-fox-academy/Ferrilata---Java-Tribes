package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Troop;
import com.greenfox.javatribes.javatribes.repositories.KingdomRepository;
import com.greenfox.javatribes.javatribes.repositories.TroopRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TroopServiceImpl implements TroopService {

    private final KingdomRepository kingdomRepository;
    private final TroopRepository troopRepository;
    private final KingdomService kingdomService;

    public TroopServiceImpl(KingdomRepository kingdomRepository, TroopRepository troopRepository, KingdomService kingdomService) {
        this.kingdomRepository = kingdomRepository;
        this.troopRepository = troopRepository;
        this.kingdomService = kingdomService;
    }

    @Override
    public Troop findById(long id) throws CustomException {

        Optional<Troop> optionalTroop = troopRepository.findById(id);

        if (!optionalTroop.isPresent()) {
            throw new CustomException("There is no troop with this Id!", HttpStatus.valueOf(404));
        }
        return optionalTroop.get();
    }

    @Override
    public void upgradeTroop(Troop troop, int level, long id) {

        Optional<Troop> optionalTroop = troopRepository.findById(id);

        if (!optionalTroop.isPresent()) {
            throw new CustomException("There is no troop with this Id!", HttpStatus.valueOf(404));
        }

        if (level < 0) {
            throw new CustomException("Invalid troop level!", HttpStatus.valueOf(400));
        }

        if (kingdomService.getGoldAmount(troop.getKingdom()) < (level - troop.getLevel()) * 5) {
            throw new CustomException("Not enough gold!", HttpStatus.valueOf(400));
        }

        kingdomService.spendGold(troop.getKingdom(), (level - troop.getLevel()) * 5);
        troop.setLevel(level);
        troopRepository.save(troop);

    }

    @Override
    public void trainTroop(Kingdom kingdom, Troop troop) throws CustomException {

        if (kingdomService.getGoldAmount(kingdom) < 10) {
            throw new CustomException("Not enough gold!", HttpStatus.valueOf(400));
        }

        kingdomService.spendGold(kingdom, 10);
        kingdom.addTroop(troop);
        kingdomRepository.save(kingdom);

    }

}
