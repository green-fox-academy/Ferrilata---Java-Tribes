package com.greenfox.javatribes.javatribes.repositories;

import com.greenfox.javatribes.javatribes.model.Kingdom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KingdomRepository extends CrudRepository<Kingdom, Long> {

    Optional<Kingdom> findById (long Id);

}
