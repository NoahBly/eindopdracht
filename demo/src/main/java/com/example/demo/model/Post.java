package com.example.demo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @Column(nullable = false, unique = false)
    private byte[] imagevideo;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public Post() {
    }

    public Post(long id, String name, byte[] imagevideo, List<Comment> comments, Profile profile) {
        this.id = id;
        this.name = name;
        this.imagevideo = imagevideo;
        this.comments = comments;
        this.profile = profile;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImagevideo() {
        return imagevideo;
    }

    public void setImagevideo(byte[] imagevideo) {
        this.imagevideo = imagevideo;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
