package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
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

    @Override
    @Transactional
    public void earnAll() {

    supplyRepository.findAll().forEach(supply -> supply.setAmount(supply.getAmount()+supply.getGeneration()));
    supplyRepository.findAll().forEach(supply -> supplyRepository.save(supply));

    }


}
