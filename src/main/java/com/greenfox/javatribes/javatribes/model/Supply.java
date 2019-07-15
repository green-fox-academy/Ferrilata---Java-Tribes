package com.greenfox.javatribes.javatribes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "supply")
public class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;  // wood + food
    private int amount = 1;
    private int generation;
    private long startedAt = new Timestamp(System.currentTimeMillis()).getTime();
    private long finishedAt = new Timestamp(startedAt + (30 * 60 * 1000)).getTime();

    public Supply() {
    }

    @JsonIgnore
    @OneToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "kingdom")
    private Kingdom kingdom;

    public Supply(String type, int amount, int generation, long startedAt, long finishedAt, Kingdom kingdom) {
        this.type = type;
        this.amount = amount;
        this.generation = generation;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.kingdom = kingdom;
    }

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
