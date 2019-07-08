package com.greenfox.javatribes.javatribes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Kingdom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kingdom_id")
    @JsonProperty("kingdomId")
    private long Id;

    @JsonIgnore
    private String name;

    @JsonIgnore
    @OneToOne(mappedBy = "kingdom")
    private User user;

    private int locationX;
    private int locationY;

    public Kingdom() {
    }

    public Kingdom(String name) {
        this.name = name;
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
}
