package com.greenfox.javatribes.javatribes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supply_id")
    private long id;

    private String type;
    private int amount;
    private int generation;
    //private int generation = this.generationGenerator();
    private Timestamp updateAt = new Timestamp(System.currentTimeMillis());

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "kingdom_id")
    private Kingdom kingdom;

    public Supply() {

    }

    public Supply(String type, int amount, int generation) {
        this.type = type;
        this.amount = amount;
        this.generation = generation;

    }

    public Supply(String type, int amount, int generation, Kingdom kingdom) {
        this.type = type;
        this.amount = amount;
        this.generation = generation;
        this.kingdom = kingdom;
    }

 /*   public int generationGenerator() {

        int generationPerMinute = 0;
        List<Building> resourceGenerators = this.kingdom.getBuildings();
        int foodConsumers = this.kingdom.getTroops().size();

        if (this.type.equalsIgnoreCase("gold")) {
            for (Building building : resourceGenerators) {
                if (building.getType().equalsIgnoreCase("mine") ||
                        building.getType().equalsIgnoreCase("townhall")) {
                    generationPerMinute = generationPerMinute + 10;
                }
            }
        }

        if (this.type.equalsIgnoreCase("food")) {
            for (Building building : resourceGenerators) {
                if (building.getType().equalsIgnoreCase("farm") ||
                        building.getType().equalsIgnoreCase("townhall")) {
                    generationPerMinute = generationPerMinute + 10;
                }
            }
            generationPerMinute = generationPerMinute - foodConsumers;
        }

        return generationPerMinute;

    }*/


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

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public void setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
    }

}
