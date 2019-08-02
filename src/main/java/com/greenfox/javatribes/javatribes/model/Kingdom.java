package com.greenfox.javatribes.javatribes.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Kingdom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kingdom_id")
    @JsonProperty("kingdomId")
    private long Id;

    private String name;
    private int locationX;
    private int locationY;

    @OneToOne(mappedBy = "kingdom")
    private User user;

    @OneToMany(mappedBy = "kingdom", cascade = CascadeType.ALL)
    private List<Supply> supplies = new ArrayList<>();

    @OneToMany(mappedBy = "kingdom", cascade = CascadeType.ALL)
    private List<Building> buildings = new ArrayList<>();

    @OneToMany(mappedBy = "kingdom", cascade = CascadeType.ALL)
    private List<Troop> troops = new ArrayList<>();

    public Kingdom(String name) {
        this.name = name;
        this.supplies = new ArrayList<>();
        this.buildings = new ArrayList<Building>();
        this.troops = new ArrayList<Troop>();
    }

    public Kingdom(String name, List<Building> buildings, List<Supply> supplies, List<Troop> troops) {
        this.name = name;
        this.buildings = buildings;
        this.supplies = supplies;
        this.troops = troops;
    }

    public Kingdom(User user, String name, List<Building> buildings, List<Supply> supplies, List<Troop> troops) {
        this.user = user;
        this.name = name;
        this.buildings = buildings;
        this.supplies = supplies;
        this.troops = troops;
    }

    public Kingdom(User user,String name, int locationX, int locationY) {
        this.user = user;
        this.name = name;
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public Kingdom(String name, int locationX, int locationY) {
        this.name = name;
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public void addSupply(Supply supply) {
        supply.setKingdom(this);
        this.supplies.add(supply);
    }

    public void addTroop(Troop troop) {
        troop.setKingdom(this);
        this.troops.add(troop);
    }

    public void addBuilding(Building building) {
        building.setKingdom(this);
        this.buildings.add(building);
    }

}
