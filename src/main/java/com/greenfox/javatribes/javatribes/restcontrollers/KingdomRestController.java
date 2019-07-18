package com.greenfox.javatribes.javatribes.restcontrollers;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.security.JwtTokenProvider;
import com.greenfox.javatribes.javatribes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class KingdomRestController {

    @Autowired
    UserService userService;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @GetMapping("/kingdom")
    public ResponseEntity<Object> displayKingdom(HttpServletRequest httpServletRequest) {

        Kingdom kingdom = userService.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(httpServletRequest))).getKingdom();
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(kingdom);

    }

    @GetMapping("/kingdom/{userId}")
    public ResponseEntity<Object> displayKingdomByUserId(@PathVariable long userId) throws CustomException {

        Kingdom kingdom = userService.findById(userId).getKingdom();
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(kingdom);

    }

    @PutMapping("/kingdom")
    public ResponseEntity<Object> updateKingdom(HttpServletRequest httpServletRequest,
                                                @RequestParam(required = false) String name,
                                                @RequestParam(required = false) int locationX,
                                                @RequestParam(required = false) int locationY) {

        userService.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(httpServletRequest))).getKingdom().setName(name);
        userService.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(httpServletRequest))).getKingdom().setLocationX(locationX);
        userService.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(httpServletRequest))).getKingdom().setLocationY(locationY);
        userService.updateUser(userService.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(httpServletRequest))));

        Kingdom modifiedKingdom = userService.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(httpServletRequest))).getKingdom();

        return ResponseEntity.status(HttpStatus.valueOf(200)).body(modifiedKingdom);

    }

    @GetMapping("/kingdom/buildings")
    public ResponseEntity<Object> getBuildingsFromKingdom(HttpServletRequest httpServletRequest) {

        Kingdom kingdom = userService.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(httpServletRequest))).getKingdom();
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(kingdom.getBuildings());
    }

    @GetMapping("/kingdom/supplies")
    public ResponseEntity<Object> getSuppliesFromKingdom(HttpServletRequest httpServletRequest) {

        Kingdom kingdom = userService.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(httpServletRequest))).getKingdom();
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(kingdom.getSupplies());
    }

    @GetMapping("/kingdom/troops")
    public ResponseEntity<Object> getTroopsFromKingdom(HttpServletRequest httpServletRequest) {

        Kingdom kingdom = userService.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(httpServletRequest))).getKingdom();
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(kingdom.getTroops());
    }
}