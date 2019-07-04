package com.greenfox.javatribes.javatribes.repositories;

import com.greenfox.javatribes.javatribes.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);
}
