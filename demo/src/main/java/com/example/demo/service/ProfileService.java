package com.example.demo.service;

import com.example.demo.dto.CreateUserProfileDto;
import com.example.demo.dto.ProfileDto;
import com.example.demo.dto.ProfileInputDto;
import com.example.demo.exceptions.RecordNotFoundException;
import com.example.demo.model.Profile;
import com.example.demo.model.User;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository repos;
    private final UserRepository repos2;

    public ProfileService(ProfileRepository repos,UserRepository repos2) {
        this.repos = repos;
        this.repos2 = repos2;
    }

    public ProfileDto createNormalProfile(CreateUserProfileDto profiledto) {
    Profile profile1 = new Profile();

    profile1.setName(profiledto.profilename);
    profile1.setType("NORMAL");
    profile1.setProfileimage(profiledto.profileimage);

    Profile profile2 = repos.save(profile1);
    ProfileDto profile3 = ProfileDto.fromProfile(profile2);

    return profile3;

    }

    public ProfileDto createCelebrityProfile(CreateUserProfileDto profiledto) {
        Profile profile1 = new Profile();

        profile1.setName(profiledto.profilename);
        profile1.setType("CELEB");
        profile1.setProfileimage(profiledto.profileimage);

        Profile profile2 = repos.save(profile1);
        ProfileDto profile3 = ProfileDto.fromProfile(profile2);

        return profile3;

    }

    public ProfileDto createPageAdminProfile(CreateUserProfileDto profiledto) {
        Profile profile1 = new Profile();

        profile1.setName(profiledto.profilename);
        profile1.setType("PAGEADMIN");
        profile1.setProfileimage(profiledto.profileimage);

        Profile profile2 = repos.save(profile1);
        ProfileDto profile3 = ProfileDto.fromProfile(profile2);

        return profile3;

    }


    public ProfileDto getProfilebyID(long profileid) {
        Optional<Profile> profile = repos.findById(profileid);

        if(profile.isEmpty()) {
                throw new RecordNotFoundException("ID can not be found");
           }else {
        Profile newProfile = repos.findById(profileid).get();
        ProfileDto newProfileDto = ProfileDto.fromProfile(newProfile);
        return newProfileDto;
           }
    }

    public ProfileDto getProfilebyUserID(long id) {
        Optional<User> user1 = repos2.findById(id);

        if(user1.isEmpty()) {
            throw new RecordNotFoundException("Cannot find id:" + id);
        }else {
            User user2 = repos2.findById(id).get();
            Profile profile1 = user2.getProfile();

            ProfileDto profile2 = ProfileDto.fromProfile(profile1);
            return profile2;
        }
    }

    public Iterable<ProfileDto>findAllProfiles() {
        Iterable<Profile> profilelist = repos.findAll();
        List<ProfileDto> profiledtolist = new ArrayList<>();
        for(Profile profile: profilelist) {
            profiledtolist.add(ProfileDto.fromProfile(profile));
        }
        return profiledtolist;
    }

    public Iterable<ProfileDto>findAllProfilesbyname(String name){
        Iterable<Profile>profilenamelist = repos.findAllByName(name);
        List<ProfileDto>profiledtonamelist = new ArrayList<>();
        for(Profile profile: profilenamelist) {
            profiledtonamelist.add(ProfileDto.fromProfile(profile));
        }
        return profiledtonamelist;
    }


    public void deleteProfilebyID(long profileid) {
        repos.deleteById(profileid);
    }


    public ProfileDto updateProfile(long profileid, ProfileDto profiledto) {
        ProfileDto newprofile = getProfilebyID(profileid);
        Profile newprofile2 = ProfileInputDto.toProfile(newprofile);

        if(!newprofile2.getName().equals(profiledto.name)) {
            newprofile2.setName(profiledto.name);
        } if(!newprofile2.getBioinformation().equals(profiledto.bioinformation)) {
            newprofile2.setBioinformation(profiledto.bioinformation);
        } if(!newprofile2.getFollowerslist().equals(profiledto.followerslist)) {
            newprofile2.setFollowerslist(profiledto.followerslist);
        } if(!newprofile2.getFollowinglist().equals(profiledto.followinglist)) {
            newprofile2.setFollowinglist(profiledto.followinglist);
        } if(!newprofile2.getFriendlist().equals(profiledto.friendlist)) {
            newprofile2.setFriendlist(profiledto.friendlist);
        }  if(!newprofile2.getPosts().equals(profiledto.posts)) {
            newprofile2.setPosts(profiledto.posts);
        } if(!newprofile2.getProfileimage().equals(profiledto.profileimage)) {
            newprofile2.setProfileimage(profiledto.profileimage);
        } if(!newprofile2.getType().equals(profiledto.type)) {
            newprofile2.setType(profiledto.type);
        }

       ProfileDto newprofile3 = ProfileDto.fromProfile(repos.save(newprofile2));

        return newprofile3;

    }

}
