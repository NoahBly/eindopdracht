package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "profiletoprofiles3")
public class ProfiletoProfile3 {
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
    private ProfiletoProfile2 otherside;


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

    public ProfiletoProfile2 getOtherside() {
        return otherside;
    }

    public void setOtherside(ProfiletoProfile2 otherside) {
        this.otherside = otherside;
    }
}
