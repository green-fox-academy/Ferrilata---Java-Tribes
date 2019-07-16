package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.model.Supply;

public interface SupplyService {

    Supply findById (long id);

    void updateSupplies(Supply supply);

    void earnById(long id);

}
