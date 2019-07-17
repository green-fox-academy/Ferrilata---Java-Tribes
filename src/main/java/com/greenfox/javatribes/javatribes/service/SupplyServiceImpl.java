package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Supply;
import com.greenfox.javatribes.javatribes.repositories.SupplyRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Configuration
@EnableScheduling
public class SupplyServiceImpl implements SupplyService {

    private SupplyRepository supplyRepository;

    public SupplyServiceImpl(SupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

    @Override
    public Supply findById(long id) throws CustomException {

        Optional<Supply> optionalSupply = supplyRepository.findById(id);

        if (!optionalSupply.isPresent()) {
            throw new CustomException("SupplyId not found!", HttpStatus.valueOf(404));
        }

        return optionalSupply.get();

    }

    @Override
    public void updateSupplies(Supply supply) {

        supplyRepository.save(supply);

    }

    @Override
    @Transactional
    public void earnById(long id) {

        findById(id).setAmount(findById(id).getAmount() + findById(id).getGeneration());
        supplyRepository.save(findById(id));

    }

    @Scheduled(fixedRate = 1000)
    public void earnSuppliesEverySecond() {

        earnById(1);

    }

}
