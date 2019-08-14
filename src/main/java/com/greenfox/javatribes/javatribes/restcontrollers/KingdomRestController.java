package com.greenfox.javatribes.javatribes.restcontrollers;

import com.greenfox.javatribes.javatribes.dto.RequestObject;
import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Role;
import com.greenfox.javatribes.javatribes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")

    @GetMapping("/kingdom/{userId}")
    public ResponseEntity<Object> displayKingdomByUserId(@PathVariable long userId) throws CustomException {

            Kingdom kingdom = userService.findById(userId).getKingdom();
            return ResponseEntity.status(HttpStatus.valueOf(200)).body(kingdom);
    }

    @PatchMapping("/kingdom")
    public ResponseEntity<Object> updateKingdom(HttpServletRequest httpServletRequest,
                                                @RequestBody @Valid RequestObject requestObject) {

        String name = requestObject.getField();
        Kingdom kingdom = userService.getUserFromToken(httpServletRequest).getKingdom();
        Kingdom updatedKingdom = userService.updateKingdom(kingdom, name);

        return ResponseEntity.status(HttpStatus.valueOf(200)).body(updatedKingdom);
    }
}