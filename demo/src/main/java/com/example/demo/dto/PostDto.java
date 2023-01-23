package com.example.demo.dto;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.Profile;
import javax.validation.constraints.NotBlank;

import java.util.List;

public class PostDto {

    @NotBlank
    public long id;

    public String name;


    public String imagevideo;

    public List<Comment> comments;

    public Profile profile;

    public static PostDto fromPost(Post post1) {
        PostDto post2 = new PostDto();
        post2.id = post1.getId();
        post2.name = post1.getName();
        post2.imagevideo = post1.getImagevideo();
        post2.comments = post1.getComments();
        post2.profile = post1.getProfile();

        return post2;
    }

}
