package com.greenfox.javatribes.javatribes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.greenfox.javatribes.javatribes.model.building.Barracks;
import com.greenfox.javatribes.javatribes.model.building.Farm;
import com.greenfox.javatribes.javatribes.model.building.Mine;
import com.greenfox.javatribes.javatribes.model.building.Townhall;
import javax.persistence.*;
import java.util.List;

@Entity
public class Kingdom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kingdom_id")
    @JsonProperty("kingdomId")
    private long Id;

    @JsonIgnore
    private String name;

    @JsonIgnore
    @OneToOne(mappedBy = "kingdom")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "townhall_id")
    private Townhall townhall;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "mine_id")
    private List<Mine> mineList;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "farm_id")
    private List<Farm> farmList;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "barracks_id")
    private List<Barracks> barracksList;

    public Kingdom() {

    }

    public Kingdom(String name) {
        this.name = name;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
