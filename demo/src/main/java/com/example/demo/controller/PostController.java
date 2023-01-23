package com.example.demo.controller;


import com.example.demo.dto.CreateUserProfileDto;
import com.example.demo.dto.PostDto;
import com.example.demo.dto.ProfileDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/posts")
public class PostController {

    PostService service;

    public PostController(PostService service) {
        this.service = service;
    }


    @PostMapping(value = "/{profileid}")
    public ResponseEntity<Object> createPost(@PathVariable("profileid") long profileid, @RequestBody PostDto dto) throws Exception{

        long postid = service.createPost(dto, profileid);

        return new ResponseEntity<>(postid, HttpStatus.CREATED);

    }

    @PostMapping(value = "/{postid}/addPostImageVideo")
    public ResponseEntity<String> addPostImageVideo(@RequestParam("file")MultipartFile file, @PathVariable("postid") long id) throws Exception{
        String filename = service.addPostimagevideo(id,file);


        return ResponseEntity.created(null).build();

    }




    @GetMapping(value = "/post/{postid}")
    public ResponseEntity<PostDto> getPostbyID(@PathVariable("postid") long id) throws Exception {
        PostDto optionalPost = service.getPostbyID(id);

        return ResponseEntity.ok().body(optionalPost);
    }


    @GetMapping(value = "/{profileid}")
    public ResponseEntity<List<PostDto>> getAllPostsbyProfileID(@PathVariable("profileid") long id){
        List<PostDto> postDtos = service.findallPostsbyProfile(id);

        return ResponseEntity.ok().body(postDtos);
    }


    @DeleteMapping(value = "/post/{postid}")
    public ResponseEntity<Object> deletePost(@PathVariable("postid") long id) {
        service.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/post/{postid}")
    public ResponseEntity<UserDto> updatePost(@PathVariable("postid") long id, @RequestBody PostDto postdto) throws Exception {
        service.updatePost(id, postdto);

        return ResponseEntity.noContent().build();
    }


}
