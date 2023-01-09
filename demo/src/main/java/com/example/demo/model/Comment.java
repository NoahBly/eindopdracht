package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false,unique = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne
    @JoinColumn(name = "commentmaker_id")
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
