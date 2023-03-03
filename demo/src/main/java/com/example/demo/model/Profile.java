package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id

    @GeneratedValue
    @Column(name = "id")
    private long id;


    private String type;

    @Column(nullable = false, unique = false)
    private String name;


    private String profileimage;


    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<ProfiletoProfile> friendlist;

    @OneToMany(mappedBy = "receiver")
    @JsonIgnore
    private List<Followrequest> followrequests;

    @OneToMany(mappedBy = "receiver")
    @JsonIgnore
    private List<Friendrequest> friendrequests;


     @OneToMany(mappedBy = "user")
     @JsonIgnore
     private List<ProfiletoProfile2> followerslist;


    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<ProfiletoProfile3> followinglist;

    private String bioinformation;

    @OneToMany(mappedBy = "profile")
    @JsonIgnore
    private List<Post> posts;

   @OneToMany(mappedBy = "commentmaker")
   @JsonIgnore
    private List<Comment> commentposts;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JsonIgnore
    //@JoinColumn(name = "profile_id", insertable=false, updatable=false)
    private User user;

    public List<Followrequest> getFollowrequests() {
        return followrequests;
    }

    public void setFollowrequests(List<Followrequest> followrequests) {
        this.followrequests = followrequests;
    }

    public List<Friendrequest> getFriendrequests() {
        return friendrequests;
    }

    public void setFriendrequests(List<Friendrequest> friendrequests) {
        this.friendrequests = friendrequests;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public List<ProfiletoProfile> getFriendlist() {
        return friendlist;
    }

    public void setFriendlist(List<ProfiletoProfile> friendlist) {
        this.friendlist = friendlist;
    }

    public List<ProfiletoProfile2> getFollowerslist() {
        return followerslist;
    }

    public void setFollowerslist(List<ProfiletoProfile2> followerslist) {
        this.followerslist = followerslist;
    }

    public List<ProfiletoProfile3> getFollowinglist() {
        return followinglist;
    }

    public void setFollowinglist(List<ProfiletoProfile3> followinglist) {
        this.followinglist = followinglist;
    }

    public String getBioinformation() {
        return bioinformation;
    }

    public void setBioinformation(String bioinformation) {
        this.bioinformation = bioinformation;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

   public List<Comment> getCommentposts() {
        return commentposts;
    }

   public void setCommentposts(List<Comment> commentpost) {
       this.commentposts = commentposts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
