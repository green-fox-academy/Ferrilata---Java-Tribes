package com.greenfox.javatribes.javatribes.restcontrollers;

import com.greenfox.javatribes.javatribes.exceptions.EntityNotFoundException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.security.JWTTokenUtil;
import com.greenfox.javatribes.javatribes.service.KingdomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class KingdomController {

    @Autowired
    KingdomService kingdomService;

    private JWTTokenUtil jwtTokenUtil;

    @GetMapping("/kingdom/")
    public Kingdom displayKingdom(JWTTokenUtil jwtTokenUtil) {

        Kingdom kingdom = new Kingdom ();
        return kingdom;
    }

    @GetMapping("/kingdom/{id}")
    public Kingdom displayKingdomById(@PathVariable long id) throws EntityNotFoundException {

        Kingdom kingdom = kingdomService.findById(id);
        return kingdom;
    }

    @PutMapping("/kingdom")
    public Kingdom updateKingdom (@RequestParam (required = false) String name, @RequestParam (required = false) int locationX, @RequestParam (required = false) int locationY) {

        Kingdom kingdom = new Kingdom ();
        kingdom.setName(name);
        kingdom.setLocationX(locationX);
        kingdom.setLocationY(locationY);
        kingdomService.saveKingdom(kingdom);

        return kingdom;

    }


}
