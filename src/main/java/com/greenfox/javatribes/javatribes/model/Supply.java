package com.greenfox.javatribes.javatribes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;  // gold + food
    private int amount = 1;
    private int generation;
    private long updateAt = new Timestamp(System.currentTimeMillis()).getTime();

    public Supply() {
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "kingdom")
    private Kingdom kingdom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(long updateAt) {
        this.updateAt = updateAt;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public void setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
    }

    public Supply(String type, int amount, int generation, long updateAt, Kingdom kingdom) {
        this.type = type;
        this.amount = amount;
        this.generation = generation;
        this.updateAt = updateAt;
        this.kingdom = kingdom;
    }
}
