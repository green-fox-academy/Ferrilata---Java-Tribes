package com.greenfox.javatribes.javatribes.repositories;

import com.greenfox.javatribes.javatribes.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsernameAndPassword (String username, String password);

    Optional<User> findById (long id);

    boolean existsByUsername(String username);

    boolean existsByKingdomName(String name);

}
