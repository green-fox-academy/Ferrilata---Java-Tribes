package com.greenfox.javatribes.javatribes;

import com.greenfox.javatribes.javatribes.dto.RegisterObject;
import com.greenfox.javatribes.javatribes.model.Building;
import com.greenfox.javatribes.javatribes.model.Role;
import com.greenfox.javatribes.javatribes.model.Troop;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final UserService userService;

    public CommandLineAppStartupRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args){

        RegisterObject adminObject = new RegisterObject("admin", "admin", "admin's kingdom");
        RegisterObject userObject = new RegisterObject("user", "user", "user's kingdom");

        User admin = userService.register(adminObject);
        User user = userService.register(userObject);
        userService.saveUser(user);
        userService.saveUser(admin);

//        MAKE CONFLICT TO TESTS - APPLICATION CONTEXT FAILED, COMMAND LINE RUNNER ERROR - NEED TO SOLVE
        admin.addRole(Role.ROLE_ADMIN);
        admin.getKingdom().addBuilding(new Building("townhall"));
        admin.getKingdom().addTroop(new Troop());

        userService.saveUser(user);
        userService.saveUser(admin);
    }
}
