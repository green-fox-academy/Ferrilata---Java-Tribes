package com.greenfox.javatribes.javatribes.repositories;

import com.greenfox.javatribes.javatribes.model.Supply;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyRepository extends CrudRepository<Supply, Long> {
}
