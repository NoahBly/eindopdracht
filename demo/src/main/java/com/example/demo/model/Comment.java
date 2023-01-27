package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false,unique = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "commentmaker_id", referencedColumnName = "id")
    @JsonIgnore
    private Profile commentmaker;


    public Comment() {
    }

    public Comment(long id, String comment, Post post, Profile commentmaker) {
        this.id = id;
        this.comment = comment;
        this.post = post;
        this.commentmaker = commentmaker;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Profile getCommentmaker() {
        return commentmaker;
    }

    public void setCommentmaker(Profile commentmaker) {
        this.commentmaker = commentmaker;
    }
}
