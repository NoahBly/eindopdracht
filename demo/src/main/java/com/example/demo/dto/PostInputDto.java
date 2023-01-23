package com.example.demo.dto;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.Profile;
import javax.validation.constraints.NotBlank;

import java.util.List;

public class PostInputDto {

    @NotBlank
    public long id;

    public String name;

    @NotBlank
    public String imagevideo;

    public List<Comment> comments;

    public Profile profile;

    public static Post toPost(PostDto post1) {
        Post post2 = new Post();
        post2.setId(post1.id);
        post2.setName(post1.name);
        post2.setImagevideo(post1.imagevideo);
        post2.setComments(post1.comments);
        post2.setProfile(post1.profile);

        return post2;
    }
}
