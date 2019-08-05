package com.greenfox.javatribes.javatribes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

import static java.lang.System.currentTimeMillis;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_id")
    private long id;

    private String type;
    private int level = 1;
    private int hp = 0;
    private Timestamp startedAt = new java.sql.Timestamp(currentTimeMillis());
    private Timestamp finishedAt = new java.sql.Timestamp(startedAt.getTime() + (40 * 1000));

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "kingdom_id")
    private Kingdom kingdom;

    public Building(String type) {
        this.type = type;
    }

}
