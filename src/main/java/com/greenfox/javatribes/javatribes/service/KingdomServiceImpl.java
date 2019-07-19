package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Supply;
import com.greenfox.javatribes.javatribes.repositories.KingdomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KingdomServiceImpl implements KingdomService {

    private KingdomRepository kingdomRepository;
    private String type;
    private Kingdom kingdom;

    public KingdomServiceImpl(KingdomRepository kingdomRepository) {
        this.kingdomRepository = kingdomRepository;
    }

    @Override
    public Supply getSupplyByType(String type, Kingdom kingdom) {
        if (!type.isEmpty() || type.equalsIgnoreCase("")) {
            throw new CustomException("Please provide supply type!", HttpStatus.valueOf(404));
        }
        if (    !type.equalsIgnoreCase("gold") ||
                !type.equalsIgnoreCase("food")) {
            throw new CustomException("There is no such supply in the kingdom!", HttpStatus.valueOf(404));
        }

        Supply supply = null;
        List<Supply> supplyList = kingdom.getSupplies();

        for (Supply s : supplyList) {
            if (s.getType().equalsIgnoreCase(type)) {
                supply = s;
            }
        }
        return supply;
    }

    /*@Override
    public Supply getSupplyByType(String type, Kingdom kingdom) {

        *//*if (!type.isEmpty() || type.equalsIgnoreCase("")) {
            throw new CustomException("Please provide supply type!", HttpStatus.valueOf(404));
        }
        if (    !type.equalsIgnoreCase("gold") ||
                !type.equalsIgnoreCase("food")) {
            throw new CustomException("There is no such supply in the kingdom!", HttpStatus.valueOf(404));
        }*//*
        Supply supply = null;
        List<Supply> supplyList = kingdom.getSupplies();

        for (Supply s : supplyList) {
            if (s.getType().equalsIgnoreCase(type)) {
                supply = s;
            }
        }
        return supply;
    }*/
}

