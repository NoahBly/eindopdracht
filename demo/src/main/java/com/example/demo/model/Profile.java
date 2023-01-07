package com.example.demo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue
    private long id;


    private String type;

    @Column(nullable = false, unique = false)
    private String name;

    @Lob
    private byte[] profileimage;

    @OneToMany
    private List<Profile> friendlist;

    @OneToMany
    private List<Followrequest> followrequests;

    @OneToMany
    private List<Friendrequest> friendrequests;

    @OneToMany
    private List<Profile> followerslist;

    @OneToMany
    private List<Profile> followinglist;

    private String bioinformation;

    @OneToMany(mappedBy = "profile")
    private List<Post> posts;

    @OneToOne(mappedBy = "profile")
    private Comment commentpost;

    @OneToOne
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

    public byte[] getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(byte[] profileimage) {
        this.profileimage = profileimage;
    }

    public List<Profile> getFriendlist() {
        return friendlist;
    }

    public void setFriendlist(List<Profile> friendlist) {
        this.friendlist = friendlist;
    }

    public List<Profile> getFollowerslist() {
        return followerslist;
    }

    public void setFollowerslist(List<Profile> followerslist) {
        this.followerslist = followerslist;
    }

    public List<Profile> getFollowinglist() {
        return followinglist;
    }

    public void setFollowinglist(List<Profile> followinglist) {
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

    public Comment getCommentpost() {
        return commentpost;
    }

    public void setCommentpost(Comment commentpost) {
        this.commentpost = commentpost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
