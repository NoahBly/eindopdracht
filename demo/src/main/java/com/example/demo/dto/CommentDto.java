package com.example.demo.dto;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.Profile;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;

public class CommentDto {

    @NotBlank
    public long id;

    @NotBlank
    public String comment;

    @NotBlank
    public Post post;

    @NotBlank
    public Profile commentmaker;


    public static CommentDto fromComment(Comment comment1) {
        CommentDto comment2 = new CommentDto();

        comment2.post = comment1.getPost();
        comment2.comment = comment1.getComment();
        comment2.commentmaker = comment1.getCommentmaker();

        return comment2;
    }

}
