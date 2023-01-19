package com.example.demo.dto;

import com.example.demo.model.*;
import javax.validation.constraints.NotBlank;

import java.util.List;

public class ProfileDto {

    @NotBlank
    public long id;

    @NotBlank
    public String type;

    @NotBlank
    public String name;


    public String profileimage;

    public List<ProfiletoProfile> friendlist;
   public List<ProfiletoProfile> followerslist;

   public List<ProfiletoProfile> followinglist;

   public List<Friendrequest> friendrequests;

   public List<Followrequest> followrequests;

   public String bioinformation;

   public List<Post> posts;

   public Comment commentpost;

   public User user;
    public static ProfileDto fromProfile(Profile profile) {
        ProfileDto profile1 = new ProfileDto();

        profile1.type = profile.getType();
        profile1.name = profile.getName();
        profile1.profileimage = profile.getProfileimage();
        profile1.followinglist = profile.getFollowinglist();
        profile1.followerslist = profile.getFollowerslist();
        profile1.bioinformation = profile.getBioinformation();
        profile1.posts = profile.getPosts();
        profile1.commentpost = profile.getCommentpost();
        profile1.followrequests = profile.getFollowrequests();
        profile1.friendrequests = profile.getFriendrequests();
        profile1.user = profile.getUser();

        return profile1;
    }


}
