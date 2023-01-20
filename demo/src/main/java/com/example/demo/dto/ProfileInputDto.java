package com.example.demo.dto;

import com.example.demo.model.*;
import javax.validation.constraints.NotBlank;

import java.util.List;

public class ProfileInputDto {

    @NotBlank
    public long id;

    @NotBlank
    public String type;

    @NotBlank
    public String name;


    public String profileimage;


    public List<Followrequest> followrequests;

    public List<Friendrequest> friendrequests;

    public List<Profile> followerslist;

    public List<Profile> followinglist;

    public String bioinformation;

    public List<Post> posts;

    public Comment commentpost;

    public User user;

    public static Profile toProfile(ProfileDto profile) {
        Profile profile1 = new Profile();
        profile1.setId(profile.id);
        profile1.setType(profile.type);
        profile1.setName(profile.name);
        profile1.setProfileimage(profile.profileimage);
        profile1.setFollowinglist(profile.followinglist);
        profile1.setFollowerslist(profile.followerslist);
        profile1.setBioinformation(profile.bioinformation);
        profile1.setPosts(profile.posts);
        profile1.setCommentpost(profile.commentpost);
        profile1.setFollowrequests(profile.followrequests);
        profile1.setFriendrequests(profile.friendrequests);
        profile1.setUser(profile.user);

        return profile1;
    }



}
