package com.greenfox.javatribes.javatribes.restcontrollers;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.User;
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

    @GetMapping("/kingdom")
    public ResponseEntity<Object> displayUserWithHisKingdom(HttpServletRequest httpServletRequest) {

        User user = userService.identifyUserFromJWTToken(httpServletRequest);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(user);

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

        userService.identifyUserKingdomFromJWTToken(httpServletRequest).setName(name);
        userService.identifyUserKingdomFromJWTToken(httpServletRequest).setLocationX(locationX);
        userService.identifyUserKingdomFromJWTToken(httpServletRequest).setLocationY(locationY);
        userService.updateUser(userService.identifyUserFromJWTToken(httpServletRequest));

        Kingdom modifiedKingdom = userService.identifyUserKingdomFromJWTToken(httpServletRequest);

        return ResponseEntity.status(HttpStatus.valueOf(200)).body(modifiedKingdom);

    }

}