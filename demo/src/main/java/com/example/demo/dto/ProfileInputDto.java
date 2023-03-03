package com.example.demo.dto;

import com.example.demo.model.*;
import javax.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

public class ProfileInputDto {

    @NotBlank
    public long id;

    @NotBlank
    public String type;

    @NotBlank
    public String name;


    public String profileimage;


    public List<FollowrequestDto> followrequests;

    public List<FriendrequestDto> friendrequests;

    public List<ProfiletoProfile2> followerslist;

    public List<ProfiletoProfile3> followinglist;
    public List<ProfiletoProfileDto>friendlist;

    public String bioinformation;

    public List<PostDto> posts;

    public List<CommentDto> commentposts;

    public UserDto user;

    public static Profile toProfile(ProfileDto profile) {
        Profile profile1 = new Profile();
        profile1.setId(profile.id);
        profile1.setType(profile.type);
        profile1.setName(profile.name);
        profile1.setProfileimage(profile.profileimage);

        List<ProfiletoProfile> List1 = new ArrayList<>();
        for(ProfiletoProfileDto pr : profile.friendlist ) {
            List1.add(ProfiletoProfileDto.toP2P(pr));
        }
        profile1.setFriendlist(List1);


        List<ProfiletoProfile3> List2 = new ArrayList<>();
        for(ProfiletoProfile3Dto pr :profile.followinglist ) {
            List2.add(ProfiletoProfile3Dto.toP2P(pr));
        }
        profile1.setFollowinglist(List2);

        List<ProfiletoProfile2> List3 = new ArrayList<>();
        for(ProfiletoProfile2Dto pr :profile.followerslist ) {
            List3.add(ProfiletoProfile2Dto.toP2P(pr));
        }

        profile1.setFollowerslist(List3);


        profile1.setBioinformation(profile.bioinformation);

        List<Post> List4 = new ArrayList<>();
        for(PostDto pr :profile.posts ) {
            List4.add(PostInputDto.toPost(pr));
        }
        profile1.setPosts(List4);

        List<Comment> List5 = new ArrayList<>();
        for(CommentDto pr :profile.commentposts ) {
            List5.add(CommentInputDto.toComment(pr));
        }
        profile1.setCommentposts(List5);


        List<Followrequest> List6 = new ArrayList<>();
        for(FollowrequestDto pr :profile.followrequests ) {
            List6.add(FollowrequestInputDto.toFollowrequest(pr));
        }
        profile1.setFollowrequests(List6);

        List<Friendrequest> List7 = new ArrayList<>();
        for(FriendrequestDto pr :profile.friendrequests) {
            List7.add(FriendrequestInputDto.toFriendrequest(pr));
        }
        profile1.setFriendrequests(List7);
        profile1.setUser(UserDto.toUser(profile.user));

        return profile1;
    }



}
