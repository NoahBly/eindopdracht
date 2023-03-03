package com.example.demo.dto;

import com.example.demo.model.Comment;
import com.example.demo.model.Friendrequest;
import com.example.demo.model.Post;
import com.example.demo.model.Profile;
import javax.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class PostInputDto {

    @NotBlank
    public long id;

    public String name;

    @NotBlank
    public String imagevideo;

    public List<CommentDto> comments;

    public ProfileDto profile;

    public static Post toPost(PostDto post1) {
        Post post2 = new Post();
        post2.setId(post1.id);
        post2.setName(post1.name);
        post2.setImagevideo(post1.imagevideo);

        List<Comment> List1 = new ArrayList<>();
        for(CommentDto pr :post1.comments) {
            List1.add(CommentInputDto.toComment(pr));
        }
        post2.setComments(List1);
        post2.setProfile(ProfileInputDto.toProfile(post1.profile));

        return post2;
    }
}
