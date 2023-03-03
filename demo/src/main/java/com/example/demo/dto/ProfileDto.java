package com.example.demo.dto;

import com.example.demo.model.*;
import javax.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class ProfileDto {

    @NotBlank
    public long id;

    @NotBlank
    public String type;

    @NotBlank
    public String name;


    public String profileimage;

    public List<ProfiletoProfileDto> friendlist;
   public List<ProfiletoProfile2Dto> followerslist;

   public List<Long> followinglist;

   public List<FriendrequestDto> friendrequests;

   public List<FollowrequestDto> followrequests;

   public String bioinformation;

   public List<PostDto> posts;

   public List<CommentDto> commentposts;

   public UserDto user;
    public static ProfileDto fromProfile(Profile profile) {
        ProfileDto profile1 = new ProfileDto();
        profile1.id = profile.getId();
        profile1.type = profile.getType();
        profile1.name = profile.getName();
        profile1.profileimage = profile.getProfileimage();


        List<ProfiletoProfileDto> List = new ArrayList<>();
        for(ProfiletoProfile friend : profile.getFriendlist() ) {
          List.add(ProfiletoProfileDto.fromP2P(friend));
        }
        profile1.friendlist = List;


        List<Long> List2 = new ArrayList<>();
        for(ProfiletoProfile3 following : profile.getFollowinglist() ) {
            List2.add(following.getId());
        }
        profile1.followinglist = List2;

        List<ProfiletoProfile2Dto> List3 = new ArrayList<>();
        for(ProfiletoProfile2 follower : profile.getFollowerslist() ) {
            List3.add(ProfiletoProfile2Dto.fromP2P(follower));
        }
        profile1.followerslist = List3;

        profile1.bioinformation = profile.getBioinformation();

        List<PostDto> List4 = new ArrayList<>();
        for(Post post : profile.getPosts() ) {
            List4.add(PostDto.fromPost(post));
        }
        profile1.posts = List4;


        List<CommentDto> List5 = new ArrayList<>();
        for(Comment comment : profile.getCommentposts() ) {
            List5.add(CommentDto.fromComment(comment));
        }

        profile1.commentposts =List5;

        List<FollowrequestDto> List6 = new ArrayList<>();
        for(Followrequest fr : profile.getFollowrequests() ) {
            List6.add(FollowrequestDto.fromFollowrequest(fr));
        }

        profile1.followrequests = List6;

        List<FriendrequestDto> List7 = new ArrayList<>();
        for(Friendrequest fr : profile.getFriendrequests() ) {
            List7.add(FriendrequestDto.fromFriendrequest(fr));
        }
        profile1.friendrequests = List7;

        UserDto user2 = new UserDto(profile.getUser().getId(),profile.getUser().getUsername(),profile.getUser().getPassword(),profile.getUser().getEnabled(),profile.getUser().getApikey(),profile.getUser().getEmail(),profile.getUser().getProfileId(),profile.getUser().getAuthorities(),null);
        profile1.user = user2;
        return profile1;
    }


}
