package com.greenfox.javatribes.javatribes;

import com.greenfox.javatribes.javatribes.model.Building;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Role;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class JavaTribesApplication implements CommandLineRunner{

    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(JavaTribesApplication.class, args);
    }


//    DB POPULATION JUST FOR TESTING PURPOSES
    @Override
    public void run(String... args) throws Exception {

        Kingdom adminKingdom = new Kingdom("admin's kingdom");
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setKingdom(adminKingdom);
        admin.setRoles(new ArrayList<>(Arrays.asList(Role.ROLE_ADMIN)));

        Kingdom userKingdom = new Kingdom("user's kingdom");
        User user = new User();
        user.setUsername("user");
        user.setPassword("user");
        user.setKingdom(userKingdom);
        user.setRoles(new ArrayList<>(Arrays.asList(Role.ROLE_USER)));

        Building townhall = new Building();
        townhall.setType("townhall");

        adminKingdom.addBuilding(townhall);

        userService.register(user);
        userService.register(admin);
    }
}
