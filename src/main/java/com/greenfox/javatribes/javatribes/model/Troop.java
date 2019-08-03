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
public class Troop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "troop_id")
    private long id;

    private int level = 1;
    private int hp = 10;
    private int attack = 1;
    private int defense = 1;
    private Timestamp startedAt = new java.sql.Timestamp(System.currentTimeMillis());
    private Timestamp finishedAt = new java.sql.Timestamp(startedAt.getTime() + (20 * 1000));

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "kingdom_id")
    private Kingdom kingdom;

    public Troop(Kingdom kingdom) {
        this.kingdom = kingdom;
    }

//    public Troop(int level) {
//        this.level = level;
//    }
}

