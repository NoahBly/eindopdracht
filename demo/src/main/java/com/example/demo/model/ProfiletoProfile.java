package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "profiletoprofiles")
public class ProfiletoProfile {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Profile user;

    @ManyToOne
    private Profile friend;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Profile getUser() {
        return user;
    }

    public void setUser(Profile user) {
        this.user = user;
    }

    public Profile getFriend() {
        return friend;
    }

    public void setFriend(Profile friend) {
        this.friend = friend;
    }
}
