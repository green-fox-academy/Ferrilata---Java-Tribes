package com.greenfox.javatribes.javatribes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supply_id")
    private long id;

    private String type;
    private int amount;
    private int generation;
    //might be not needed
    private Timestamp updateAt = new Timestamp(System.currentTimeMillis());

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "kingdom_id")
    private Kingdom kingdom;

    /*public Supply() {
    }*/

    public Supply(String type, int amount, Kingdom kingdom) {
        this.type = type;
        this.amount = amount;

    }

    public Supply(String type, int amount) {
        this.type = type;
        this.amount = amount;

    }

    public Supply(String type, int amount, int generation, Kingdom kingdom) {
        this.type = type;
        this.amount = amount;
        this.kingdom = kingdom;
    }

    /*public long getId() {
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
    }*/

}
