package com.greenfox.javatribes.javatribes.restcontrollers;

import com.greenfox.javatribes.javatribes.exceptions.UserIdNotFoundException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class KingdomRestController {

    @Autowired
    UserService userService;

    //token not used at the moment. might be used in future version of the code.
    //private JWTTokenUtil jwtTokenUtil;

    //this end point should eventually return kingdom of the active (logged in) user (based on token verification?)
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

    //this end point should eventually edit name and location of kingdom of the active (logged in) user (based on token verification?)
    @PutMapping("/kingdom")
    public ResponseEntity<Object> updateKingdom(@RequestBody @Valid User user,
                                                @RequestParam(required = false) String name,
                                                @RequestParam(required = false) int locationX,
                                                @RequestParam(required = false) int locationY) throws UserIdNotFoundException {

        userService.findByUsername(user.getUsername()).getKingdom().setName(name);
        userService.findByUsername(user.getUsername()).getKingdom().setLocationX(locationX);
        userService.findByUsername(user.getUsername()).getKingdom().setLocationY(locationY);
        userService.updateUser(userService.findByUsername(user.getUsername()));

        Kingdom modifiedKingdom = userService.findByUsername(user.getUsername()).getKingdom();

        return ResponseEntity.status(HttpStatus.valueOf(200)).body(modifiedKingdom);

    }

}
