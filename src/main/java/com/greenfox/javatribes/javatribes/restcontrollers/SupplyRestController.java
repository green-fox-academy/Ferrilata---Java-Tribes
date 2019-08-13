package com.greenfox.javatribes.javatribes.restcontrollers;

import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Supply;
import com.greenfox.javatribes.javatribes.service.SupplyService;
import com.greenfox.javatribes.javatribes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/kingdom")
public class SupplyRestController {

    @Autowired
    UserService userService;
    @Autowired
    SupplyService supplyService;

    @GetMapping("/supplies")
    public ResponseEntity<Object> getSuppliesFromKingdom(HttpServletRequest httpServletRequest) {

        Kingdom kingdom = userService.getUserFromToken(httpServletRequest).getKingdom();
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(kingdom.getSupplies());
    }

    @GetMapping("/supplies/{type}")
    public ResponseEntity<Object> getSuppliesFromKingdomByType(HttpServletRequest httpServletRequest,
                                                         @PathVariable String type) {

        Kingdom kingdom = userService.getUserFromToken(httpServletRequest).getKingdom();
        Supply supply = supplyService.findByKingdomAndType(kingdom, type);

        return ResponseEntity.status(HttpStatus.valueOf(200)).body(supply);
    }
}
