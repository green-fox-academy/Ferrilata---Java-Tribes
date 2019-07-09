package com.greenfox.javatribes.javatribes.restcontrollers;

import com.greenfox.javatribes.javatribes.model.Building;
import com.greenfox.javatribes.javatribes.repositories.BuildingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildingController {

    BuildingRepository buildingRepository;

    public BuildingController(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @PostMapping("/building")
    public ResponseEntity saveBuilding(@RequestBody Building building) {
        buildingRepository.save(building);
        return ResponseEntity.ok().body(building);
    }
}
