package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.model.Role;
import com.greenfox.javatribes.javatribes.repositories.RoleRepository;

public interface RoleService {

    Role findByName(String name);
    void addRole(Role role);
}
