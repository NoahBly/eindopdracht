package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.exceptions.RecordNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FollowrequestService {

    private final ProfileRepository repos2;
    private final FollowrequestRepository repos;

    private final ProfiletoProfileRepository repos3;
    private final ProfiletoProfile2Repository repos4;
    private final ProfiletoProfile3Repository repos5;

    public FollowrequestService(ProfileRepository repos2, FollowrequestRepository repos, ProfiletoProfileRepository repos3,ProfiletoProfile2Repository repos4, ProfiletoProfile3Repository repos5) {
        this.repos2 = repos2;
        this.repos = repos;
        this.repos3 = repos3;
        this.repos4 = repos4;
        this.repos5 = repos5;
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

            ProfiletoProfile3 proftoprof = new ProfiletoProfile3();
            ProfiletoProfile2 proftoprof2 = new ProfiletoProfile2();

            proftoprof.setUser(maker);
            proftoprof.setFriend(receiver);
            proftoprof2.setUser(receiver);
            proftoprof2.setFriend(maker);
            repos5.save(proftoprof);
            repos4.save(proftoprof2);

            List<ProfiletoProfile3> followinglist2 = maker.getFollowinglist();
            List<ProfiletoProfile2> followerslist = receiver.getFollowerslist();
            followerslist.add(proftoprof2);
            followinglist2.add(proftoprof);
            receiver.setFollowerslist(followerslist);
            repos2.save(receiver);

            maker.setFollowinglist(followinglist2);
            repos2.save(maker);

        }
    }



    public List<ProfiletoProfile2Dto>getAllFollowersbyProfileID(long id) {
        Optional<Profile> profile1 = repos2.findById(id);
        if(profile1.isEmpty()) {
            throw new RecordNotFoundException("ID can not be found");
        }else {
            Profile profile2 = repos2.findById(id).get();
            List<ProfiletoProfile2> Followerslist = profile2.getFollowerslist();
            List<ProfiletoProfile2Dto> Followerslist2 = new ArrayList<>();
            for(ProfiletoProfile2 profile: Followerslist) {
                Followerslist2.add(ProfiletoProfile2Dto.fromP2P(profile));
            }
            return Followerslist2;
        }
    }



    public void deleteFollowerbyID(long id, long id2) {
        List<ProfiletoProfile2Dto> Followerslist = getAllFollowersbyProfileID(id);
        List<ProfiletoProfile2> Followerslist2 = new ArrayList<>();
        for(ProfiletoProfile2Dto profile: Followerslist) {
            Followerslist2.add(ProfiletoProfile2Dto.toP2P(profile));
        }

        Optional<ProfiletoProfile2> profiletoprofile1 = repos4.findById(id2);
        if (profiletoprofile1.isEmpty()) {
            throw new RecordNotFoundException("ID can not be found");
        } else {
            ProfiletoProfile2 profiletoprofile2 = repos4.findById(id2).get();
            Followerslist2.remove(profiletoprofile2);
            Profile follower = profiletoprofile2.getFriend();

            List<ProfiletoProfile3>Followinglist = follower.getFollowinglist();
            Profile profile3 = repos2.findById(id).get();

            Followinglist.removeIf(p2p -> p2p.getFriend().equals(profile3));

            follower.setFollowinglist(Followinglist);
            profile3.setFollowerslist(Followerslist2);
            repos2.save(profile3);
            repos2.save(follower);
        }
    }



    public List<ProfiletoProfile3Dto>getAllFollowingsbyProfileID(long id) {
        Optional<Profile>profile1 = repos2.findById(id);
        if(profile1.isEmpty()) {
            throw new RecordNotFoundException("ID can not be found");
        }else {
            Profile profile2 = repos2.findById(id).get();
            List<ProfiletoProfile3> Followinglist = profile2.getFollowinglist();
            List<ProfiletoProfile3Dto> Followinglist2 = new ArrayList<>();
            for(ProfiletoProfile3 profile: Followinglist) {
                Followinglist2.add(ProfiletoProfile3Dto.fromP2P(profile));
            }
            return Followinglist2;
        }
    }



    public void deleteFollowingbyID(long id, long id2) {
        List<ProfiletoProfile3Dto> Followinglist = getAllFollowingsbyProfileID(id);
        List<ProfiletoProfile3> Followinglist2 = new ArrayList<>();
        for(ProfiletoProfile3Dto profile: Followinglist) {
            Followinglist2.add(ProfiletoProfile3Dto.toP2P(profile));
        }

        Optional<ProfiletoProfile3> profiletoprofile1 = repos5.findById(id2);
        if (profiletoprofile1.isEmpty()) {
            throw new RecordNotFoundException("ID can not be found");
        } else {
            ProfiletoProfile3 profiletoprofile2 = repos5.findById(id2).get();
            Followinglist2.remove(profiletoprofile2);
            Profile followed = profiletoprofile2.getFriend();
            List<ProfiletoProfile2>followerslist = followed.getFollowerslist();
            Profile profile3 = repos2.findById(id).get();
            followerslist.removeIf(p2p -> p2p.getFriend().equals(profile3));
            followed.setFollowerslist(followerslist);
            profile3.setFollowinglist(Followinglist2);
            repos2.save(profile3);
            repos2.save(followed);
        }
    }

}
