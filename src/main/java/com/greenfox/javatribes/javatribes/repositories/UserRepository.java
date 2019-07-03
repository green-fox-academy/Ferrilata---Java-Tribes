package com.greenfox.javatribes.javatribes.repositories;

import com.greenfox.javatribes.javatribes.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsernameAndPassword (String username, String password);
    Optional<User> findByUsername (String username);

    boolean existsByUsername(String username);

}
