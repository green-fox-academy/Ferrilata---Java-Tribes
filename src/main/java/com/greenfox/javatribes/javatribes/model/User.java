package com.greenfox.javatribes.javatribes.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @NotNull @NotEmpty
    private String username;

    @NotNull @NotEmpty
    private String password;

    @JsonUnwrapped
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kingdom_id")
//    @JsonFilter("KingdomFilter")
    private Kingdom kingdom;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL, orphanRemoval = true)
    private Set<Authorities> roles;

    public User(User user) {
    }

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
        this.roles = new HashSet<>(Arrays.asList(new Authorities("USER", this)));
    }

    public User(@NotNull @NotEmpty String username, @NotNull @NotEmpty String password, Set<Authorities> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
//        this.roles = new HashSet<>(Arrays.asList(new Authorities("USER", this)));

    }

    public Set<Authorities> getRoles() {
        return roles;
    }

    public void setRoles(Set<Authorities> roles) {
        this.roles = roles;
    }

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
