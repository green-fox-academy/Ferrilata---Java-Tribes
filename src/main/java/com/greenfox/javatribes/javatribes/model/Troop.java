package com.greenfox.javatribes.javatribes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

@Entity
public class Troop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int level = 1;
    private int hp = 0;
    private int attack;
    private int defense;
    private long startedAt = new java.sql.Timestamp(System.currentTimeMillis()).getTime();
    private long finishedAt = new java.sql.Timestamp(startedAt + (30 * 60 * 1000)).getTime();

    @JsonIgnore
    @ManyToOne()//(fetch = FetchType.LAZY)
    private Kingdom kingdom;

    public Troop() {

    }

    public Troop(int level, int hp, int attack, int defense, long startedAt, long finishedAt, Kingdom kingdom) {
        this.level = level;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
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

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
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
