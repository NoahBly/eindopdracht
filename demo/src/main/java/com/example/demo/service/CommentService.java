package com.example.demo.service;

import com.example.demo.dto.CommentDto;
import com.example.demo.exceptions.RecordNotFoundException;
import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.Profile;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.ProfileRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository repos;

    private final PostRepository repos2;

    private final ProfileRepository repos3;

    public CommentService(CommentRepository repos, PostRepository repos2, ProfileRepository repos3) {
        this.repos = repos;
        this.repos2 = repos2;
        this.repos3 = repos3;
    }

    public Long createComment(CommentDto commentdto, long postid, long profilemakerid ) {
        Comment comment1 = new Comment();
        Post post1 = repos2.findById(postid).get();
        Profile commentmaker =repos3.findById(profilemakerid).get();

        comment1.setComment(commentdto.comment);
        comment1.setCommentmaker(commentmaker);
        comment1.setPost(post1);
        repos.save(comment1);

        List<Comment> comments = new ArrayList<>(post1.getComments());
        comments.add(comment1);
        post1.setComments(comments);
        repos2.save(post1);

        return comment1.getId();


    }

    public CommentDto getCommentbyID(long id) {
        Optional<Comment> comment1 = repos.findById(id);
        if (comment1.isEmpty()) {
            throw new RecordNotFoundException("Cannot find id:" + id);
        } else {
            Comment comment2 = repos.findById(id).get();
            CommentDto comment3 = CommentDto.fromComment(comment2);
            return comment3;
        }
    }

    public List<CommentDto>findallCommentsbyPost(long postid) {
        Optional<Post> post1 = repos2.findById(postid);
        if (post1.isEmpty()) {
            throw new RecordNotFoundException("Cannot find id:" + postid);
        } else {
            Post post2 = repos2.findById(postid).get();
            List<Comment> commentlist = new ArrayList<>(post2.getComments());
            List<CommentDto> commentlist2 = new ArrayList<>();

            for (Comment comment : commentlist) {
                commentlist2.add(CommentDto.fromComment(comment));
            }

            return commentlist2;

        }
    }




    public void deleteCommentbyid(long commentid, String username) {
        Optional<Comment> comment1 = repos.findById(commentid);
        if (comment1.isEmpty()) {
            throw new RecordNotFoundException("Cannot find id:" + commentid);
        } else {
            Comment comment2 = repos.findById(commentid).get();
           // String username = SecurityContextHolder.getContext().getAuthentication().getName();
           // String usernameame = principal.getUsername();
            if (username.equals(comment2.getCommentmaker().getUser().getUsername()) || username.equals(comment2.getPost().getProfile().getUser().getUsername())) {
                repos.deleteById(commentid);
            }
        }


    }
}
