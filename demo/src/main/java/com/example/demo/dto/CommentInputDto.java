package com.example.demo.dto;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.Profile;
import javax.validation.constraints.NotBlank;

public class CommentInputDto {

    @NotBlank
    public long id;

    @NotBlank
    public String comment;

    @NotBlank
    public PostDto post;

    @NotBlank
    public ProfileDto commentmaker;


    public static Comment toComment(CommentDto comment1) {
        Comment comment2 = new Comment();
        comment2.setId(comment1.id);
        comment2.setPost(PostInputDto.toPost(comment1.post));
        comment2.setComment(comment1.comment);
        comment2.setCommentmaker(ProfileInputDto.toProfile(comment1.commentmaker));

        return comment2;
    }
}
