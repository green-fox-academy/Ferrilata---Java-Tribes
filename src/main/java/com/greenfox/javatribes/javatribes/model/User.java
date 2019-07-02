package com.greenfox.javatribes.javatribes.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;

    @NotNull @NotEmpty
    private String username;

    @NotNull @NotEmpty
    private String password;

    @OneToOne(cascade =  CascadeType.ALL,
            mappedBy = "user")
    @JsonUnwrapped
//    @JsonFilter("KingdomFilter")
    private Kingdom kingdom;



    public User() {
    }

    public User(String username, String password, Kingdom kingdom) {
        this.username = username;
        this.password = password;
        this.kingdom = kingdom;
    }

    public User(@NotNull @NotEmpty String username, @NotNull @NotEmpty String password) {
        this.username = username;
        this.password = password;
    }

    //    public User(@NotNull @NotEmpty String username, @NotNull @NotEmpty String password, Kingdom kingdom) {
//        this.username = username;
//        this.password = password;
//        this.kingdom = kingdom;
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public void setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
    }
}
