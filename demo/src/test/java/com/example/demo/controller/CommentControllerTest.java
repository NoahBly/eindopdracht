package com.example.demo.controller;

import com.example.demo.dto.CommentDto;
import com.example.demo.dto.PostDto;
import com.example.demo.model.*;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.service.CommentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

   @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private PostRepository postRepository;

    CommentDto commentdto;

    CommentDto commentdto2;

    Comment comment;

    Profile maker1;

    User user1;

    Post post1;



    @BeforeEach
    void setUp() {
        if(commentRepository.count()>0) {
            commentRepository.deleteAll();
        }

        if(postRepository.count()>0) {
            postRepository.deleteAll();
        }
        if(profileRepository.count()>0) {
            profileRepository.deleteAll();
        }

        List<Comment> commentposts =new ArrayList<>();
        List<Comment> commentposts2 =new ArrayList<>();


        maker1 = new Profile(1,"NORMAL","keesjan",null, null,null,null,null,null,null,null,commentposts, user1);
        maker1 = profileRepository.save(maker1);
        commentdto = new CommentDto("testcomment",post1,maker1);
        commentdto2 = new CommentDto(3,"testcomment",post1,maker1);

        post1 = new Post(2,"testpost",null,commentposts2,maker1);
        post1 = postRepository.save(post1);
        comment = new Comment(3,"testcomment",post1,maker1);
        comment =commentRepository.save(comment);



    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createComment() throws Exception {


        mockMvc.perform(post("/comments/post/"+ post1.getId() + "/profile/"+maker1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(commentdto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("4"));
    }

    @Test
    void getCommentbyID() throws Exception {


       MvcResult Result = mockMvc.perform(get("/comments/"+ comment.getId()))

                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(comment.getId()))
                .andExpect(jsonPath("comment").value("testcomment"))
//                .andExpect(jsonPath("commentmaker").value(asJsonString(maker1)))
//                .andExpect(jsonPath("post").value(asJsonString(post1)));
                .andReturn();
                String actual = Result.getResponse().getContentAsString();
                String expected = asJsonString(comment);
                assertEquals(expected,actual);

    }

    public static String asJsonString(final CommentDto obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String asJsonString(final Comment obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String asJsonString(final Post obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String asJsonString(final Profile obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}