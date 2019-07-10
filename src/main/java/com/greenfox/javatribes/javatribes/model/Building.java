package com.greenfox.javatribes.javatribes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfox.javatribes.javatribes.repositories.BuildingRepository;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String type;
    private int level = 1;
    private int hp = 0;
    private long startedAt = new Timestamp(System.currentTimeMillis()).getTime();
    private long finishedAt = new Timestamp(startedAt + (30 * 60 * 1000)).getTime();

    @JsonIgnore
    @ManyToOne()//(fetch = FetchType.LAZY)
    private Kingdom kingdom;

    public Building() {

    }

    public Building(String type, int level, int hp, long startedAt, long finishedAt, Kingdom kingdom) {
        this.type = type;
        this.level = level;
        this.hp = hp;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.kingdom = kingdom;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public long getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(long startedAt) {
        this.startedAt = startedAt;
    }

    public long getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(long finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public void setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
    }
}
