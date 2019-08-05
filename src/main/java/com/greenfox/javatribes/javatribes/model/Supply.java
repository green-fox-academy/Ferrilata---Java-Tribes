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

    public Supply(String type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public Supply(String type) {
        this.type = type;
    }

}
