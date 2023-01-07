package com.example.demo.service;

import com.example.demo.dto.PostDto;
import com.example.demo.dto.ProfileDto;
import com.example.demo.exceptions.RecordNotFoundException;
import com.example.demo.model.Post;
import com.example.demo.model.Profile;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository repos;
    private final ProfileRepository repos2;

    public PostService(PostRepository repos, ProfileRepository repos2) {
        this.repos = repos;
        this.repos2 = repos2;
    }

    public long createPost(PostDto postdto, long profileid) {
        Post post1 = new Post();
        Profile profile1 = repos2.findById(profileid).get();
        post1.setName(postdto.name);
        post1.setImagevideo(postdto.imagevideo);
        Post post2 = repos.save(post1);
        List<Post>profileposts = new ArrayList<>(profile1.getPosts());
        profileposts.add(post2);
        profile1.setPosts(profileposts);
        repos2.save(profile1);
        return post2.getId();
    }

    public PostDto getPostbyID(long postid) {
        Optional<Post> post1 = repos.findById(postid);
        if (post1.isEmpty()) {
            throw new RecordNotFoundException("Cannot find id:" + postid);
        } else {
            Post newPost = repos.findById(postid).get();
            PostDto newPostDto = PostDto.fromPost(newPost);
            return newPostDto;

        }
    }

        public List<PostDto> findallPostsbyProfile ( long profileid){

            Optional<Profile> profile1 = repos2.findById(profileid);

            if (profile1.isEmpty()) {
                throw new RecordNotFoundException("Cannot find id:" + profileid);
            } else {
                Profile profile2 = repos2.findById(profileid).get();
                ProfileDto profile3 = new ProfileDto();
                List<Post> profileposts = new ArrayList<>(profile2.getPosts());
                List<PostDto> profilepostdtolist = new ArrayList<>();
                for (Post post : profileposts) {
                    profilepostdtolist.add(PostDto.fromPost(post));
                }
                return profilepostdtolist;
            }
        }


    public void deletePost(long postid) {
        Optional<Post> post1 = repos.findById(postid);
        if (post1.isEmpty()) {
            throw new RecordNotFoundException("Cannot find id:" + postid);
        } else {
            repos.deleteById(postid);
        }
    }


    public PostDto updatePost(long postid ,PostDto postdto) {
        Optional<Post> post1 = repos.findById(postid);
        if (post1.isEmpty()) {
            throw new RecordNotFoundException("Cannot find id:" + postid);
        } else {
            Post post2 = repos.findById(postid).get();

            if (!post2.getName().equals(postdto.name)) {
                post2.setName(postdto.name);
            }
            PostDto post3 = PostDto.fromPost(repos.save(post2));

            return post3;
        }
    }
}
