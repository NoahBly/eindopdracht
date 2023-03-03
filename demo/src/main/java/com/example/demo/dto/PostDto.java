package com.example.demo.dto;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.Profile;
import com.example.demo.model.ProfiletoProfile3;

import javax.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class PostDto {

    @NotBlank
    public long id;

    public String name;


    public String imagevideo;

    public List<CommentDto> comments;

    public ProfileDto profile;

    public static PostDto fromPost(Post post1) {
        PostDto post2 = new PostDto();
        post2.id = post1.getId();
        post2.name = post1.getName();
        post2.imagevideo = post1.getImagevideo();

        List<CommentDto> List1 = new ArrayList<>();
        for(Comment cm : post1.getComments() ) {
            List1.add(CommentDto.fromComment(cm));
        }

        post2.comments = List1;

        post2.profile = ProfileDto.fromProfile(post1.getProfile());

        return post2;
    }

}
