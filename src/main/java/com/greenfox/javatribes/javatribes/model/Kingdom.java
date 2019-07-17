package com.greenfox.javatribes.javatribes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Kingdom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kingdom_id")
    @JsonProperty("kingdomId")
    private long Id;

    @JsonIgnore
    private String name;
    private int locationX;
    private int locationY;

    @JsonIgnore
    @OneToOne(mappedBy = "kingdom")
    private User user;

    @OneToMany(mappedBy = "kingdom", cascade = CascadeType.ALL)
    private List<Supply> supplies = new ArrayList<>();

    @OneToMany(mappedBy = "kingdom", cascade = CascadeType.ALL)
    private List<Building> buildings = new ArrayList<>();

    @OneToMany(mappedBy = "kingdom", cascade = CascadeType.ALL)
    private List<Troop> troops = new ArrayList<>();

    public Kingdom() {
    }

    public Kingdom(String name) {
        this.name = name;
        this.supplies = new ArrayList<Supply>();
        this.supplies.add(new Supply("gold", 5, 5));
        this.supplies.add(new Supply("food", 5, 5));
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

    public int getGoldAmount() {

        int goldAmount = 0;

        for (Supply supply : supplies) {

            if (supply.getType().equalsIgnoreCase("gold")) {

                goldAmount = supply.getAmount();

            }

        }

        return goldAmount;

    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Supply> getSupplies() {
        return supplies;
    }

    public void setSupplies(List<Supply> supplies) {
        this.supplies = supplies;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

    public List<Troop> getTroops() {
        return troops;
    }

    public void setTroops(List<Troop> troops) {
        this.troops = troops;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }
}
