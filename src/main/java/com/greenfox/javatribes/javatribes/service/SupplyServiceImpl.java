package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Supply;
import com.greenfox.javatribes.javatribes.repositories.SupplyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SupplyServiceImpl implements SupplyService {

    private SupplyRepository supplyRepository;

    public SupplyServiceImpl(SupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

    @Override
    public Supply findById(long id) throws CustomException {

        Optional<Supply> optionalSupply = supplyRepository.findById(id);

        if(!optionalSupply.isPresent()) {
            throw new CustomException("SupplyId not found!", HttpStatus.valueOf(404));
        }

        return optionalSupply.get();

    }

    @Override
    public void updateSupplies(Supply supply) {

        supplyRepository.save(supply);

    }

    @Override
    public void earnById(long id) {

        supplyRepository.findById(id).get().setAmount(supplyRepository.findById(id).get().getAmount() + supplyRepository.findById(id).get().getGeneration());
        supplyRepository.save(supplyRepository.findById(id).get());

    }
}
