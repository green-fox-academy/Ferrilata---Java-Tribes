package com.greenfox.javatribes.javatribes;

import com.greenfox.javatribes.javatribes.dto.RegisterObject;
import com.greenfox.javatribes.javatribes.model.Building;
import com.greenfox.javatribes.javatribes.model.Role;
import com.greenfox.javatribes.javatribes.model.Troop;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaTribesApplication implements CommandLineRunner {

    private final UserService userService;

    public JavaTribesApplication(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(JavaTribesApplication.class, args);
    }

    //    DB POPULATION JUST FOR TESTING PURPOSES
    @Override
    public void run(String... args) throws Exception {

        RegisterObject adminObject = new RegisterObject("admin", "admin", "admin's kingdom");
        RegisterObject userObject = new RegisterObject("user", "user", "user's kingdom");

        User admin = userService.register(adminObject);
        User user = userService.register(userObject);
        userService.saveUser(user);
        userService.saveUser(admin);

//        MAKE CONFLICT TO TESTS - APPLICATION CONTEXT FAILED, COMMAND LINE RUNNER ERROR - NEED TO SOLVE
//        admin.addRole(Role.ROLE_ADMIN);
//        admin.getKingdom().addBuilding(new Building("townhall"));
//        admin.getKingdom().addTroop(new Troop());

        userService.saveUser(user);
        userService.saveUser(admin);
    }
}
