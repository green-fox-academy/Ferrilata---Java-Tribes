package com.greenfox.javatribes.javatribes.service;

import com.greenfox.javatribes.javatribes.model.Role;
import com.greenfox.javatribes.javatribes.repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public void addRole(Role newRole) {

        roleRepository.save(newRole);
    }
}
