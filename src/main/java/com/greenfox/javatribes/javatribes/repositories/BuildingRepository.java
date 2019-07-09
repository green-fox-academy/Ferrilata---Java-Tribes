package com.greenfox.javatribes.javatribes.repositories;

import com.greenfox.javatribes.javatribes.model.building.Building;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends CrudRepository<Building, Long> {

}
