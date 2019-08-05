package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Supply;
import com.greenfox.javatribes.javatribes.repositories.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class SupplyServiceImpl implements SupplyService {

    @Autowired
    SupplyRepository supplyRepository;
    @Autowired
    TimerService timerService;
    @Autowired
    BuildingService buildingService;

    @Override
    public Supply findById(long id) throws CustomException {

        Optional<Supply> optionalSupply = supplyRepository.findById(id);

        if (!optionalSupply.isPresent()) {
            throw new CustomException("SupplyId not found!", HttpStatus.valueOf(404));
        }

        return optionalSupply.get();

    }

    @Override
    public Supply findByKingdomAndType(Kingdom kingdom, String type) {

        Optional<Supply> optionalSupply = supplyRepository.findByKingdomAndType(kingdom, type);

        if (!optionalSupply.isPresent()) {
            throw new CustomException("There is no such supply type in the kingdom!", HttpStatus.valueOf(404));
        }
        return optionalSupply.get();
    }

    @Override
    @Transactional
    public void earnAll() {

        supplyRepository.findAll().forEach(this::generationRecalculator);
        supplyRepository.findAll().forEach(supply -> supply.setAmount(supply.getAmount() + supply.getGeneration()));
        supplyRepository.findAll().forEach(supplyRepository::save);

    }

    @Override
    public void generationRecalculator(Supply supply) {

        int generationPerMinute = 0;
        int foodConsumers = (int) supply.getKingdom().getTroops().stream().filter(timerService::finishedTroop).count();

        if (supply.getType().equalsIgnoreCase("gold")) {

            generationPerMinute = buildingService.finishedBuildingCalculator(supply, "townhall") * 10
                    + buildingService.finishedBuildingCalculator(supply, "mine") * 10;
        }

        if (supply.getType().equalsIgnoreCase("food")) {

            generationPerMinute = buildingService.finishedBuildingCalculator(supply, "townhall") * 10
                    + buildingService.finishedBuildingCalculator(supply, "farm") * 10
                    - foodConsumers;
        }
        supply.setGeneration(generationPerMinute);
    }
}
