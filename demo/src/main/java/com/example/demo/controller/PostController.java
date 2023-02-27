package com.example.demo.controller;


import com.example.demo.dto.CreateUserProfileDto;
import com.example.demo.dto.PostDto;
import com.example.demo.dto.ProfileDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.PostService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    @PostMapping(value = "/step/{postid}/addPostImageVideo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addPostImageVideo(@RequestParam("file")MultipartFile file, @PathVariable("postid") long id) throws Exception{
        String filename = service.addPostimagevideo(id,file);


        return ResponseEntity.created(null).build();

    }




    @GetMapping(value = "/post/{postid}")
    public ResponseEntity<PostDto> getPostbyID(@PathVariable("postid") long id) throws Exception {
        PostDto optionalPost = service.getPostbyID(id);

        return ResponseEntity.ok().body(optionalPost);
    }


    @GetMapping("/downloadpostfile/{postid}")
    ResponseEntity<Resource> Findpostfilebypostid(@PathVariable("postid") long postid, HttpServletRequest request) {

        Resource resource = service.downLoadPostfile(postid);

//        this mediaType decides witch type you accept if you only accept 1 type
//        MediaType contentType = MediaType.IMAGE_JPEG;
//        this is going to accept multiple types
        String mimeType;

        try{
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

//        for download attachment use next line
//        return ResponseEntity.ok().contentType(contentType).header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + resource.getFilename()).body(resource);
//        for showing image in browser
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
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
