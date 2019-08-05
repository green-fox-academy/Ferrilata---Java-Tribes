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
@RequestMapping("/kingdom")
public class TroopRestController {

    @Autowired
    UserService userService;
    @Autowired
    TroopService troopService;

    @GetMapping("/troops")
    public ResponseEntity<Object> getTroopsFromKingdom(HttpServletRequest httpServletRequest) {

        Kingdom kingdom = userService.getUserFromToken(httpServletRequest).getKingdom();
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(kingdom.getTroops());
    }

    @GetMapping("/troops/{troopId}")
    public ResponseEntity<Object> displayTroopById(@PathVariable long troopId,
                                                   HttpServletRequest httpServletRequest) throws CustomException {

        Kingdom kingdom = userService.getUserFromToken(httpServletRequest).getKingdom();
        Troop troop = troopService.findByIdAndKingdom(troopId, kingdom);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(troop);
    }

    @PostMapping("/troops")
    public ResponseEntity<Object> trainTroop(HttpServletRequest httpServletRequest) {

        Kingdom kingdom = userService.getUserFromToken(httpServletRequest).getKingdom();
        Troop newTroop = troopService.trainTroop(kingdom);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(newTroop);
    }

    @PatchMapping("/troops/{troopId}")
    public ResponseEntity<Object> upgradeTroop(@PathVariable long troopId, @RequestParam int level,
                                               HttpServletRequest httpServletRequest) throws CustomException {

        Kingdom kingdom = userService.getUserFromToken(httpServletRequest).getKingdom();
        Troop upgradedTroop = troopService.upgradeTroop(kingdom, level, troopId);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(upgradedTroop);
    }
}
