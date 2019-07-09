package com.greenfox.javatribes.javatribes.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String type;
    private int level = 1;
    private int hp;
    private long startedAt = new Timestamp(System.currentTimeMillis()).getTime();
    private long finishedAt = new Timestamp(startedAt + (30 * 60 * 1000)).getTime();

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "kingdom_id")
    private Kingdom kingdom;

    public long getId() {
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
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
}
