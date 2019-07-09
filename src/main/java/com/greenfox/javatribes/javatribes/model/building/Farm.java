package com.greenfox.javatribes.javatribes.model.building;

import com.greenfox.javatribes.javatribes.model.Kingdom;

import javax.persistence.*;

public class Farm extends Building {

    private String type = "farm";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id")
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





