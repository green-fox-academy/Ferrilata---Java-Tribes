package com.greenfox.javatribes.javatribes.model.building;

import com.greenfox.javatribes.javatribes.model.Kingdom;

import javax.persistence.*;

public class Barracks extends Building {

    private String type = "baracks";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barracks_id")
    private Kingdom kingdom;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public void setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
    }
}

