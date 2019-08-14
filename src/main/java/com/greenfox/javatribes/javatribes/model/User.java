package com.greenfox.javatribes.javatribes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    @ToString.Exclude
    private String password;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kingdom_id")
    private Kingdom kingdom;

    public User(String username, String password, Kingdom kingdom) {
        this.username = username;
        this.password = password;
        this.kingdom = kingdom;
        this.roles = new ArrayList<>();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.roles = new ArrayList<>();
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }
}
