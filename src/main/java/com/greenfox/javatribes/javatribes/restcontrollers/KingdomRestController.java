package com.greenfox.javatribes.javatribes.restcontrollers;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Role;
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
    public ResponseEntity<Object> displayKingdom(HttpServletRequest httpServletRequest) {

        Kingdom kingdom = userService.getUserFromToken(httpServletRequest).getKingdom();
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(kingdom);
    }

//    REDUNDANT ENDPOINT FOR USERS IF WE HAVE ONLY ONE KINGDOM PER USER AND WE AUTHENTICATE USER FROM JWT TOKEN
//    SHOULD BE ACCESSED BY ADMIN ONLY
    @GetMapping("/kingdom/{userId}")
    public ResponseEntity<Object> displayKingdomByUserId(@PathVariable long userId,
                                                         HttpServletRequest httpServletRequest) throws CustomException {

        if (userService.getUserFromToken(httpServletRequest).getRoles().contains(Role.ROLE_ADMIN)){
            Kingdom kingdom = userService.findById(userId).getKingdom();
            return ResponseEntity.status(HttpStatus.valueOf(200)).body(kingdom);
        } else {
            throw new CustomException("You are not authorized as ADMIN", HttpStatus.valueOf(403));
        }
    }

    @PatchMapping("/kingdom")
    public ResponseEntity<Object> updateKingdom(HttpServletRequest httpServletRequest,
                                                @RequestParam(required = false) String name,
                                                @RequestParam(required = false) int locationX,
                                                @RequestParam(required = false) int locationY) {

        Kingdom kingdom = userService.getUserFromToken(httpServletRequest).getKingdom();
        Kingdom updatedKingdom = userService.updateKingdom(kingdom, name, locationX, locationY);

        return ResponseEntity.status(HttpStatus.valueOf(200)).body(updatedKingdom);
    }
}