package com.example.demo.service;

import com.example.demo.Interface.InterfcFilesStorageService;
import com.example.demo.dto.PostDto;
import com.example.demo.dto.ProfileDto;
import com.example.demo.exceptions.RecordNotFoundException;
import com.example.demo.model.Post;
import com.example.demo.model.Profile;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private final Path root = Paths.get("uploads");

    public long createPost(PostDto postdto, long profileid) {


        Post post1 = new Post();
        Profile profile1 = repos2.findById(profileid).get();
        post1.setName(postdto.name);
        Post post2 = repos.save(post1);
        List<Post> profileposts = new ArrayList<>(profile1.getPosts());
        profileposts.add(post2);
        profile1.setPosts(profileposts);
        repos2.save(profile1);
        return post2.getId();

    }

    public String addPostimagevideo (long postid,MultipartFile file) {

        String filename = "";
        long fileID = 0;
        Profile newProfile = null;
        Optional<Post> post = repos.findById(postid);

        if (post.isEmpty()) {
            throw new RecordNotFoundException("ID can not be found");
        } else {
            Post newPost = repos.findById(postid).get();

            try {
                Files.createDirectories(root);
            } catch (IOException e) {
                throw new RuntimeException("Could not initialize folder for upload!");
            }
            try {
                fileID = Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
                filename = file.getOriginalFilename();

            } catch (Exception e) {
                if (e instanceof FileAlreadyExistsException) {
                    throw new RuntimeException("A file of that name already exists.");
                }
            }
            newPost.setImagevideo(filename);
            repos.save(newPost);
            return filename;
        }
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
