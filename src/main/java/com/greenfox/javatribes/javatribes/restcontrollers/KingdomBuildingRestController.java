package com.greenfox.javatribes.javatribes.restcontrollers;

import com.greenfox.javatribes.javatribes.exceptions.CustomException;
import com.greenfox.javatribes.javatribes.model.Building;
import com.greenfox.javatribes.javatribes.model.Kingdom;
import com.greenfox.javatribes.javatribes.service.BuildingService;
import com.greenfox.javatribes.javatribes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class KingdomBuildingRestController {

    @Autowired
    UserService userService;
    @Autowired
    BuildingService buildingService;

    @GetMapping("/kingdom/buildings")
    public ResponseEntity<Object> getBuildingsFromKingdom(HttpServletRequest httpServletRequest) {

        Kingdom kingdom = userService.identifyUserKingdomFromJWTToken(httpServletRequest);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(kingdom.getBuildings());
    }

    @GetMapping("/kingdom/buildings/{buildingId}")
    public ResponseEntity<Object> displayBuildingById(HttpServletRequest httpServletRequest, @PathVariable long buildingId) throws CustomException {

        Building building = buildingService.findById(buildingId);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(building);

    }

    @PutMapping("/kingdom/buildings/{buildingId}")
    public ResponseEntity<Object> upgradeBuilding(HttpServletRequest httpServletRequest,
                                                  @PathVariable long buildingId,
                                                  @RequestParam int level) throws CustomException {

        Building upgradedBuilding = buildingService.findById(buildingId);
        buildingService.upgradeBuilding(upgradedBuilding, level, buildingId);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(upgradedBuilding);

    }

    @PostMapping("kingdom/buildings")
    public ResponseEntity<Object> buildBuilding(HttpServletRequest httpServletRequest,
                                                @RequestParam(required = true) String type) {

        Kingdom kingdom = userService.identifyUserKingdomFromJWTToken(httpServletRequest);

        Building building = new Building(type, kingdom);
        buildingService.constructBuilding(kingdom, building);

        return ResponseEntity.status(HttpStatus.valueOf(200)).body(building);

    }

}
