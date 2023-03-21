package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "profiletoprofiles")
public class ProfiletoProfile {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "profile_id")
    private Profile user;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Profile friend;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private ProfiletoProfile otherside;

    public ProfiletoProfile(long id, Profile user, Profile friend, ProfiletoProfile otherside) {
        this.id = id;
        this.user = user;
        this.friend = friend;
        this.otherside = otherside;
    }

    public ProfiletoProfile() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public ProfiletoProfile getOtherside() {
        return otherside;
    }

    public void setOtherside(ProfiletoProfile otherside) {
        this.otherside = otherside;
    }
}
