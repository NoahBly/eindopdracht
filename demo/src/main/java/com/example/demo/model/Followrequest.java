package com.example.demo.model;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "followrequests")
public class Followrequest {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile maker;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private Profile receiver;

    public Followrequest() {
    }

    public Followrequest(long id, Profile maker, Profile receiver) {
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
