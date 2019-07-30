package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Troop;
import com.greenfox.javatribes.javatribes.repositories.KingdomRepository;
import com.greenfox.javatribes.javatribes.repositories.TroopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TroopServiceImpl implements TroopService {

    @Autowired
    private KingdomRepository kingdomRepository;

    @Autowired
    private TroopRepository troopRepository;

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

        if (troop.getKingdom().getGoldAmount() < (level - troop.getLevel()) * 5) {
            throw new CustomException("Not enough gold!", HttpStatus.valueOf(400));
        }

        troop.getKingdom().spendGold((level - troop.getLevel()) * 5);
        troop.setLevel(level);
        troopRepository.save(troop);

    }

    @Override
    public void trainTroop(Kingdom kingdom, Troop troop) throws CustomException {

        if (kingdom.getGoldAmount() < 10) {
            throw new CustomException("Not enough gold!", HttpStatus.valueOf(400));
        }

        kingdom.spendGold(10);
        kingdom.addTroop(troop);
        //kingdom.getSupplies().forEach(supply -> supply.generationRecalculator());
        kingdomRepository.save(kingdom);

    }

    @Override
    @Transactional
    public void finishTroops() {

        //troopRepository.findAll().forEach(troop -> troop.finishProduction());
        troopRepository.findAll().forEach(troop -> troopRepository.save(troop));

    }

}
