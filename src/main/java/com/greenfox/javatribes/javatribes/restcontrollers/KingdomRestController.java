package com.greenfox.javatribes.javatribes.restcontrollers;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Building;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.model.Troop;
import com.greenfox.javatribes.javatribes.security.JwtTokenProvider;
import com.greenfox.javatribes.javatribes.service.*;
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
    TroopService troopService;
    @Autowired
    BuildingService buildingService;
    @Autowired
    SupplyService supplyService;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    TimerService timerService;

    @GetMapping("/kingdom")
    public ResponseEntity<Object> displayKingdom(HttpServletRequest httpServletRequest) {

        Kingdom kingdom = userService.findByUsername(userService.identifyUser(httpServletRequest)).getKingdom();
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

        userService.findByUsername(userService.identifyUser(httpServletRequest)).getKingdom().setName(name);
        userService.findByUsername(userService.identifyUser(httpServletRequest)).getKingdom().setLocationX(locationX);
        userService.findByUsername(userService.identifyUser(httpServletRequest)).getKingdom().setLocationY(locationY);
        userService.updateUser(userService.findByUsername(userService.identifyUser(httpServletRequest)));

        Kingdom modifiedKingdom = userService.findByUsername(userService.identifyUser(httpServletRequest)).getKingdom();

        return ResponseEntity.status(HttpStatus.valueOf(200)).body(modifiedKingdom);

    }

    @PostMapping("kingdom/troops")
    public ResponseEntity<Object> trainTroop(HttpServletRequest httpServletRequest) {

        Kingdom kingdom = userService.findByUsername(userService.identifyUser(httpServletRequest)).getKingdom();

        Troop troop = new Troop(kingdom);
        troopService.trainTroop(kingdom, troop);
        //timerService.finishTroop(troop);

        /*@Scheduled(fixedDelay = 30000)
        public void finishTroop() {

            troop.setReady(true);

        }*/

        return ResponseEntity.status(HttpStatus.valueOf(200)).body(troop);

    }

    @PostMapping("kingdom/buildings")
    public ResponseEntity<Object> buildBuilding(HttpServletRequest httpServletRequest,
                                                @RequestParam(required = true) String type) {

        Kingdom kingdom = userService.findByUsername(userService.identifyUser(httpServletRequest)).getKingdom();

        Building building = new Building(type, kingdom);
        buildingService.constructBuilding(kingdom, building);

        return ResponseEntity.status(HttpStatus.valueOf(200)).body(building);

    }

}