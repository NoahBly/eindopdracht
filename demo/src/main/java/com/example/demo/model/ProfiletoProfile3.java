package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "profiletoprofiles3")
public class ProfiletoProfile3 {
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
    private ProfiletoProfile2 otherside;

    public ProfiletoProfile3(long id, Profile user, Profile friend, ProfiletoProfile2 otherside) {
        this.id = id;
        this.user = user;
        this.friend = friend;
        this.otherside = otherside;
    }

    public ProfiletoProfile3() {
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

    public ProfiletoProfile2 getOtherside() {
        return otherside;
    }

    public void setOtherside(ProfiletoProfile2 otherside) {
        this.otherside = otherside;
    }
}
