package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "friendrequests")
public class Friendrequest {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    private Profile maker;

    @OneToOne
    private Profile receiver;

    public Friendrequest() {
    }

    public Friendrequest(long id, Profile maker, Profile receiver) {
        this.id = id;
        this.maker = maker;
        this.receiver = receiver;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Profile getMaker() {
        return maker;
    }

    public void setMaker(Profile maker) {
        this.maker = maker;
    }

    public Profile getReceiver() {
        return receiver;
    }

    public void setReceiver(Profile receiver) {
        this.receiver = receiver;
    }
}
