package com.greenfox.javatribes.javatribes.restcontrollers;

import com.greenfox.javatribes.javatribes.exceptions.KingdomNotFoundException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.ResponseObject;
import com.greenfox.javatribes.javatribes.model.User;
import com.greenfox.javatribes.javatribes.security.JWTTokenUtil;
import com.greenfox.javatribes.javatribes.service.KingdomService;
import com.greenfox.javatribes.javatribes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class KingdomController {

    @Autowired
    KingdomService kingdomService;
    UserService userservice;
    User authUser;

    private JWTTokenUtil jwtTokenUtil;

    @GetMapping("/kingdom/")
    public ResponseEntity<Object> displayKingdom(JWTTokenUtil jwtTokenUtil) {

        Kingdom kingdom = new Kingdom ();

        return ResponseEntity.status(HttpStatus.valueOf(200)).body(kingdom);
    }

    @GetMapping("/kingdom/{id}")
    public ResponseEntity<Object> displayKingdomById(@PathVariable long id) throws KingdomNotFoundException {

        Kingdom kingdom = kingdomService.findById(id);

        return ResponseEntity.status(HttpStatus.valueOf(200)).body(kingdom);

    }

    @PutMapping("/kingdom")
    public ResponseEntity<Object> updateKingdom (@RequestParam (required = false) String name, @RequestParam (required = false) int locationX, @RequestParam (required = false) int locationY) {

        Kingdom kingdom = new Kingdom ();
        kingdom.setName(name);
        kingdom.setLocationX(locationX);
        kingdom.setLocationY(locationY);
        kingdomService.saveKingdom(kingdom);

        return ResponseEntity.status(HttpStatus.valueOf(200)).body(new ResponseObject("ok",
                null, jwtTokenUtil.getToken(authUser)));

    }


}
