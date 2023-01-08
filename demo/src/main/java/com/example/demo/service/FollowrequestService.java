package com.example.demo.service;

import com.example.demo.dto.FollowrequestDto;
import com.example.demo.dto.FriendrequestDto;
import com.example.demo.dto.ProfileDto;
import com.example.demo.dto.ProfileInputDto;
import com.example.demo.exceptions.RecordNotFoundException;
import com.example.demo.model.Followrequest;
import com.example.demo.model.Friendrequest;
import com.example.demo.model.Profile;
import com.example.demo.repository.FollowrequestRepository;

import com.example.demo.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FollowrequestService {

    private final ProfileRepository repos2;
    private final FollowrequestRepository repos;

    public FollowrequestService(ProfileRepository repos2, FollowrequestRepository repos) {
        this.repos2 = repos2;
        this.repos = repos;
    }

    public long createFollowrequest(long profileidmaker, long profileidreceiver){
        Followrequest followrequest1 = new Followrequest();

        Profile maker = repos2.findById(profileidmaker).get();

        Profile receiver = repos2.findById(profileidreceiver).get();


       followrequest1.setMaker(maker);
        followrequest1.setReceiver(receiver);
        Followrequest followrequest2 = repos.save(followrequest1);
        List<Followrequest> followrequests = receiver.getFollowrequests();

        followrequests.add(followrequest2);

        receiver.setFollowrequests(followrequests);

        repos2.save(receiver);

        return followrequest2.getId();

    }


    public FollowrequestDto getFollowrequestbyID(long id) {
        Optional<Followrequest> followrequest = repos.findById(id);
        if (followrequest.isEmpty()) {
            throw new RecordNotFoundException("Cannot find id:" + id);
        } else {

            Followrequest followrequest1 = repos.findById(id).get();
            FollowrequestDto followrequestDto = FollowrequestDto.fromFollowrequest(followrequest1);

            return followrequestDto;

        }
    }



    public List<FollowrequestDto>findAllFollowrequestsbyProfile(long profileid) {
        Optional<Profile> profile1 = repos2.findById(profileid);
        if (profile1.isEmpty()) {
            throw new RecordNotFoundException("Cannot find id:" + profileid);
        } else {
          Profile profile2 = repos2.findById(profileid).get();

            List<Followrequest> followrequests = new ArrayList<>(profile2.getFollowrequests());
            List<FollowrequestDto> followrequestsDto = new ArrayList<>();

            for (Followrequest followrequest : followrequests) {
                followrequestsDto.add(FollowrequestDto.fromFollowrequest(followrequest));
            }
            return followrequestsDto;
        }
    }



    public void deleteFollowrequestbyid(long id) {
        Optional<Followrequest> followrequest = repos.findById(id);
        if (followrequest.isEmpty()) {
            throw new RecordNotFoundException("Cannot find id:" + id);
        } else {
            repos.deleteById(id);
        }
    }



    public void acceptFollowrequestbyID(long id) {
        Optional<Followrequest> followrequest1 = repos.findById(id);

        if (followrequest1.isEmpty()) {
            throw new RecordNotFoundException("Cannot find id:" + id);
        } else {
            Followrequest followrequest2 = repos.findById(id).get();

            Profile receiver = followrequest2.getReceiver();
            Profile maker = followrequest2.getMaker();

            List<Profile> followinglist2 = maker.getFollowinglist();
            List<Profile> followerslist = receiver.getFollowerslist();
            followerslist.add(maker);
            followinglist2.add(receiver);
            receiver.setFollowerslist(followerslist);
            repos2.save(receiver);

            maker.setFollowinglist(followinglist2);
            repos2.save(maker);

        }
    }



    public List<ProfileDto>getAllFollowersbyProfileID(long id) {
        Optional<Profile> profile1 = repos2.findById(id);
        if(profile1.isEmpty()) {
            throw new RecordNotFoundException("ID can not be found");
        }else {
            Profile profile2 = repos2.findById(id).get();
            List<Profile> Followerslist = profile2.getFollowerslist();
            List<ProfileDto> Followerslist2 = new ArrayList<>();
            for(Profile profile: Followerslist) {
                Followerslist2.add(ProfileDto.fromProfile(profile));
            }
            return Followerslist2;
        }
    }



    public void deleteFollowerbyID(long id, long id2) {
        List<ProfileDto> Followerslist = getAllFollowersbyProfileID(id);
        List<Profile> Followerslist2 = new ArrayList<>();
        for(ProfileDto profile: Followerslist) {
            Followerslist2.add(ProfileInputDto.toProfile(profile));
        }

        Optional<Profile> profile1 = repos2.findById(id2);
        if (profile1.isEmpty()) {
            throw new RecordNotFoundException("ID can not be found");
        } else {
            Profile profile2 = repos2.findById(id2).get();
            Followerslist2.remove(profile2);
            Profile profile3 = repos2.findById(id).get();
            profile3.setFollowerslist(Followerslist2);
            repos2.save(profile3);
        }
    }



    public List<ProfileDto>getAllFollowingsbyProfileID(long id) {
        Optional<Profile>profile1 = repos2.findById(id);
        if(profile1.isEmpty()) {
            throw new RecordNotFoundException("ID can not be found");
        }else {
            Profile profile2 = repos2.findById(id).get();
            List<Profile> Followinglist = profile2.getFollowinglist();
            List<ProfileDto> Followinglist2 = new ArrayList<>();
            for(Profile profile: Followinglist) {
                Followinglist2.add(ProfileDto.fromProfile(profile));
            }
            return Followinglist2;
        }
    }



    public void deleteFollowingbyID(long id, long id2) {
        List<ProfileDto> Followinglist = getAllFollowingsbyProfileID(id);
        List<Profile> Followinglist2 = new ArrayList<>();
        for(ProfileDto profile: Followinglist) {
            Followinglist2.add(ProfileInputDto.toProfile(profile));
        }

        Optional<Profile> profile1 = repos2.findById(id2);
        if (profile1.isEmpty()) {
            throw new RecordNotFoundException("ID can not be found");
        } else {
            Profile profile2 = repos2.findById(id2).get();
            Followinglist2.remove(profile2);
            Profile profile3 = repos2.findById(id).get();
            profile3.setFollowinglist(Followinglist2);
            repos2.save(profile3);
        }
    }

}
