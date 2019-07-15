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
    @JsonProperty("kingdomId")
    private long Id;

    @JsonIgnore
    private String name;

    @OneToMany(mappedBy = "kingdom", cascade = CascadeType.ALL)
    private List<Supply> supplies;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "kingdom", cascade = CascadeType.ALL)//(fetch = FetchType.LAZY, mappedBy = "kingdom")
    private List<Building> buildings = new ArrayList<>();

    @OneToMany(mappedBy = "kingdom")//(fetch = FetchType.LAZY, mappedBy = "kingdom")
    private List<Troop> troops  = new ArrayList<>();;

    private int locationX;
    private int locationY;

    public Kingdom() {

    }

    public Kingdom(String name) {
        this.name = name;
    }

    public Kingdom(String name, int locationX, int locationY) {
        this.name = name;
        this.locationX = locationX;
        this.locationY = locationY;
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

    public int getLocationY() {
        return locationY;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }
  
  public List<Supply> getSupplies() {
        return supplies;
    }

    public void setSupplies(List<Supply> supplies) {
        this.supplies = supplies;
    }
}
