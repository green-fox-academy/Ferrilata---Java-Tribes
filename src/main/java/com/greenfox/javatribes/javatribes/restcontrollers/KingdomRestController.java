package com.greenfox.javatribes.javatribes.restcontrollers;

import com.greenfox.javatribes.javatribes.exceptions.UserIdNotFoundException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.security.JWTTokenUtil;
import com.greenfox.javatribes.javatribes.service.KingdomService;
import com.greenfox.javatribes.javatribes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class KingdomRestController {

    @Autowired
    KingdomService kingdomService;
    @Autowired
    UserService userService;

    /*@Autowired
    UserRepository repo;*/

    private JWTTokenUtil jwtTokenUtil;

    @GetMapping("/kingdom")
    public ResponseEntity<Object> displayKingdom(@RequestBody @Valid User user) throws UserIdNotFoundException {

        Kingdom kingdom = userService.findByUsername(user.getUsername()).getKingdom();
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(kingdom);

    }

    @GetMapping("/kingdom/{userId}")
    public ResponseEntity<Object> displayKingdomByUserId(@PathVariable long userId) throws UserIdNotFoundException {

        Kingdom kingdom = userService.findById(userId).getKingdom();
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(kingdom);

    }

    /*@GetMapping("/user/{userId}")
    public ResponseEntity<Object> displayUserById(@PathVariable long userId) throws UserIdNotFoundException {

       User user = userService.findById(userId);

        return ResponseEntity.status(HttpStatus.valueOf(200)).body(user);

    }

    @GetMapping("/username/{userId}")
    public String displayUserNameByUserId(@PathVariable long userId) throws UserIdNotFoundException {

        String name = userService.findById(userId).getUsername();

        return name;

    }*/

    /*@GetMapping("/users")
    public ResponseEntity<Object> displayAllUsers() throws UserIdNotFoundException {

        Iterable<User> userList = repo.findAll();

        return ResponseEntity.status(HttpStatus.valueOf(200)).body(userList);

    }*/

    /*@GetMapping("/kingdomId/{id}")
    public ResponseEntity<Object> displayKingdomById(@PathVariable long id) throws UserIdNotFoundException {

        Kingdom kingdom = kingdomService.findById(id);

        return ResponseEntity.status(HttpStatus.valueOf(200)).body(kingdom);

    }*/

    @PutMapping("/kingdom")
    public ResponseEntity<Object> updateKingdom (@RequestBody @Valid User user,
                                                 @RequestParam(required = false) String name,
                                                 @RequestParam (required = false) int locationX,
                                                 @RequestParam (required = false) int locationY) throws UserIdNotFoundException {

        userService.findByUsername(user.getUsername()).getKingdom().setName(name);
        userService.findByUsername(user.getUsername()).getKingdom().setLocationX(locationX);
        userService.findByUsername(user.getUsername()).getKingdom().setLocationY(locationY);
        userService.updateUser(userService.findByUsername(user.getUsername()));

        return ResponseEntity.status(HttpStatus.valueOf(200)).body("ok");

    }

}
