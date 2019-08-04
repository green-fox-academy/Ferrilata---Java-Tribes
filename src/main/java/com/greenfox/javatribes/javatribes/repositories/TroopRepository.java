package com.greenfox.javatribes.javatribes.repositories;

import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Troop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TroopRepository extends CrudRepository<Troop, Long> {

    Optional<Troop> findByIdAndKingdom(long id, Kingdom kingdom);
}
