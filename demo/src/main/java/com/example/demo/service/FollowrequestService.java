package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.exceptions.RecordNotFoundException;
import com.example.demo.model.Followrequest;
import com.example.demo.model.Friendrequest;
import com.example.demo.model.Profile;
import com.example.demo.model.ProfiletoProfile;
import com.example.demo.repository.FollowrequestRepository;

import com.example.demo.repository.ProfileRepository;
import com.example.demo.repository.ProfiletoProfileRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FollowrequestService {

    private final ProfileRepository repos2;
    private final FollowrequestRepository repos;

    private final ProfiletoProfileRepository repos3;

    public FollowrequestService(ProfileRepository repos2, FollowrequestRepository repos, ProfiletoProfileRepository repos3) {
        this.repos2 = repos2;
        this.repos = repos;
        this.repos3 = repos3;
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

            ProfiletoProfile proftoprof = new ProfiletoProfile();
            ProfiletoProfile proftoprof2 = new ProfiletoProfile();

            proftoprof.setUser(maker);
            proftoprof.setFriend(receiver);
            proftoprof2.setUser(receiver);
            proftoprof2.setFriend(maker);
            repos3.save(proftoprof);
            repos3.save(proftoprof2);

            List<ProfiletoProfile> followinglist2 = maker.getFollowinglist();
            List<ProfiletoProfile> followerslist = receiver.getFollowerslist();
            followerslist.add(proftoprof2);
            followinglist2.add(proftoprof);
            receiver.setFollowerslist(followerslist);
            repos2.save(receiver);

            maker.setFollowinglist(followinglist2);
            repos2.save(maker);

        }
    }



    public List<ProfiletoProfileDto>getAllFollowersbyProfileID(long id) {
        Optional<Profile> profile1 = repos2.findById(id);
        if(profile1.isEmpty()) {
            throw new RecordNotFoundException("ID can not be found");
        }else {
            Profile profile2 = repos2.findById(id).get();
            List<ProfiletoProfile> Followerslist = profile2.getFollowerslist();
            List<ProfiletoProfileDto> Followerslist2 = new ArrayList<>();
            for(ProfiletoProfile profile: Followerslist) {
                Followerslist2.add(ProfiletoProfileDto.fromP2P(profile));
            }
            return Followerslist2;
        }
    }



    public void deleteFollowerbyID(long id, long id2) {
        List<ProfiletoProfileDto> Followerslist = getAllFollowersbyProfileID(id);
        List<ProfiletoProfile> Followerslist2 = new ArrayList<>();
        for(ProfiletoProfileDto profile: Followerslist) {
            Followerslist2.add(ProfiletoProfileDto.toP2P(profile));
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



    public List<ProfiletoProfileDto>getAllFollowingsbyProfileID(long id) {
        Optional<Profile>profile1 = repos2.findById(id);
        if(profile1.isEmpty()) {
            throw new RecordNotFoundException("ID can not be found");
        }else {
            Profile profile2 = repos2.findById(id).get();
            List<ProfiletoProfile> Followinglist = profile2.getFollowinglist();
            List<ProfiletoProfileDto> Followinglist2 = new ArrayList<>();
            for(ProfiletoProfile profile: Followinglist) {
                Followinglist2.add(ProfiletoProfileDto.fromP2P(profile));
            }
            return Followinglist2;
        }
    }



    public void deleteFollowingbyID(long id, long id2) {
        List<ProfiletoProfileDto> Followinglist = getAllFollowingsbyProfileID(id);
        List<ProfiletoProfile> Followinglist2 = new ArrayList<>();
        for(ProfiletoProfileDto profile: Followinglist) {
            Followinglist2.add(ProfiletoProfileDto.toP2P(profile));
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
