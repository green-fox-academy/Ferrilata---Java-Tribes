package com.greenfox.javatribes.javatribes.restcontrollers;

import com.greenfox.javatribes.javatribes.dto.RequestObject;
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
import javax.validation.Valid;

@RestController
@RequestMapping("/kingdom")
public class BuildingRestController {

    @Autowired
    UserService userService;
    @Autowired
    BuildingService buildingService;

    @GetMapping("/buildings")
    public ResponseEntity<Object> getBuildingsFromKingdom(HttpServletRequest httpServletRequest) {

        Kingdom kingdom = userService.getUserFromToken(httpServletRequest).getKingdom();
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(kingdom.getBuildings());
    }

    @GetMapping("/buildings/{buildingId}")
    public ResponseEntity<Object> displayBuildingById(HttpServletRequest httpServletRequest,
                                                      @PathVariable long buildingId) throws CustomException {

        Kingdom kingdom = userService.getUserFromToken(httpServletRequest).getKingdom();
        Building building = buildingService.findByIdAndKingdom(buildingId, kingdom);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(building);
    }

    @PatchMapping("/buildings/{buildingId}")
    public ResponseEntity<Object> upgradeBuilding(HttpServletRequest httpServletRequest,
                                                  @PathVariable long buildingId,
                                                  @RequestBody @Valid RequestObject requestObject) throws CustomException {

        int level = Integer.parseInt(requestObject.getField());
        Kingdom kingdom = userService.getUserFromToken(httpServletRequest).getKingdom();
        Building upgradedBuilding = buildingService.upgradeBuilding(kingdom, level, buildingId);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(upgradedBuilding);
    }

    @PostMapping("/buildings")
    public ResponseEntity<Object> newBuilding(HttpServletRequest httpServletRequest,
                                              @RequestBody @Valid RequestObject requestObject) {

        String type = requestObject.getField();
        Kingdom kingdom = userService.getUserFromToken(httpServletRequest).getKingdom();
        Building newBuilding = buildingService.constructBuilding(kingdom, type);

        return ResponseEntity.status(HttpStatus.valueOf(200)).body(newBuilding);
    }
}
