package com.greenfox.javatribes.javatribes.restcontrollers;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Troop;
import com.greenfox.javatribes.javatribes.service.TroopService;
import com.greenfox.javatribes.javatribes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class KingdomTroopRestController {

    @Autowired
    UserService userService;
    @Autowired
    TroopService troopService;

    @GetMapping("/kingdom/troops")
    public ResponseEntity<Object> getTroopsFromKingdom(HttpServletRequest httpServletRequest) {

        Kingdom kingdom = userService.identifyUserKingdomFromJWTToken(httpServletRequest);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(kingdom.getTroops());

    }

    @GetMapping("/kingdom/troops/{troopId}")
    public ResponseEntity<Object> displayTroopById(HttpServletRequest httpServletRequest,
                                                         @PathVariable long troopId) throws CustomException {

        Troop troop = troopService.findById(troopId);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(troop);

    }

    @PostMapping("kingdom/troops")
    public ResponseEntity<Object> trainTroop(HttpServletRequest httpServletRequest) {

        Kingdom kingdom = userService.identifyUserKingdomFromJWTToken(httpServletRequest);

        Troop troop = new Troop(kingdom);

        troopService.trainTroop(kingdom, troop);

        return ResponseEntity.status(HttpStatus.valueOf(200)).body(troop);

    }

    @PutMapping("/kingdom/troops/{troopId}")
    public ResponseEntity<Object> upgradeTroop(HttpServletRequest httpServletRequest,
                                                  @PathVariable long troopId,
                                                  @RequestParam int level) throws CustomException {

        Troop upgradedTroop = troopService.findById(troopId);
        troopService.upgradeTroop(upgradedTroop, level, troopId);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(upgradedTroop);

    }

}
