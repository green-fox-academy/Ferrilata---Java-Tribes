package com.greenfox.javatribes.javatribes.model;

import javax.persistence.*;

@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private long id;

    private String role;

    public Authority() {
    }

    public Authority(String role) {
        this.role = role;
    }
}
