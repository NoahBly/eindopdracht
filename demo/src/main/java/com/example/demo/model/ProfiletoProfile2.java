package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "profiletoprofiles2")
public class ProfiletoProfile2 {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile user;

    @ManyToOne
    private Profile friend;


    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private ProfiletoProfile3 otherside;

    public ProfiletoProfile2(long id, Profile user, Profile friend, ProfiletoProfile3 otherside) {
        this.id = id;
        this.user = user;
        this.friend = friend;
        this.otherside = otherside;
    }

    public ProfiletoProfile2() {
    }

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

    public ProfiletoProfile3 getOtherside() {
        return otherside;
    }

    public void setOtherside(ProfiletoProfile3 otherside) {
        this.otherside = otherside;
    }
}
