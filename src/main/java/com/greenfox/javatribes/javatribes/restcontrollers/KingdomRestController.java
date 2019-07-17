package com.greenfox.javatribes.javatribes.restcontrollers;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.security.JwtTokenProvider;
import com.greenfox.javatribes.javatribes.service.SupplyService;
import com.greenfox.javatribes.javatribes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Configuration
@EnableScheduling
public class KingdomRestController {

    @Autowired
    UserService userService;
    @Autowired
    SupplyService supplyService;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping ("/earn")
    public void earn() {

        supplyService.earnById(1);

    }


    @PostMapping ("/add/{id}")
    public void add(long id) {

        supplyService.findById(id).setAmount(supplyService.findById(id).getAmount() + supplyService.findById(id).getGeneration());
        supplyService.updateSupplies(supplyService.findById(id));

    }

    @PostMapping ("/earn/{id}")
    public void earn(long id) {

        supplyService.earnById(id);

    }


    @GetMapping("/test/{id}")
    public String displaySupplbyId(long id) {

        String amount2 = String.valueOf(supplyService.findById(id).getAmount());
        return amount2;

    }

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

}