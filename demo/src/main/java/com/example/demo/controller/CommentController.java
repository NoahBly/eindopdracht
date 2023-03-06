package com.example.demo.controller;


import com.example.demo.dto.CommentDto;
import com.example.demo.dto.FollowrequestDto;
import com.example.demo.dto.IdinputDto;
import com.example.demo.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/comments")
public class CommentController {

    CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping(value = "/post/{postid}/profile/{profileidmaker}")
    public ResponseEntity<Object> createComment(@PathVariable ("postid") long postid, @PathVariable("profileidmaker") Long profileidmaker, @RequestBody CommentDto dto) throws Exception{

        long commentid = service.createComment(dto,postid,profileidmaker);

        return new ResponseEntity<>(commentid, HttpStatus.CREATED);

    }



    @GetMapping(value = "/{commentid}")
    public ResponseEntity<CommentDto> getCommentbyID(@PathVariable("commentid") long id) throws Exception {
        CommentDto optionalComment = service.getCommentbyID(id);

        return ResponseEntity.ok().body(optionalComment);
    }



    @GetMapping(value = "/post/{postid}")
    public ResponseEntity<List<CommentDto>> getAllCommentsbyPostID(@PathVariable("postid") long postid){
        List<CommentDto> commentDtos = service.findallCommentsbyPost(postid);

        return ResponseEntity.ok().body(commentDtos);
    }


    @DeleteMapping(value = "/{commentid}")
    public ResponseEntity<Object> deleteCommentbyID(@PathVariable("commentid") long id, Principal principal) {
        service.deleteCommentbyid(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

}
