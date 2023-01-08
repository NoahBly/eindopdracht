package com.example.demo.service;

import com.example.demo.dto.FriendrequestDto;
import com.example.demo.dto.FriendrequestInputDto;
import com.example.demo.dto.ProfileDto;
import com.example.demo.dto.ProfileInputDto;
import com.example.demo.exceptions.RecordNotFoundException;
import com.example.demo.model.Friendrequest;
import com.example.demo.model.Profile;
import com.example.demo.repository.FriendrequestRepository;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FriendrequestService {

    private final UserRepository repos3;
    private final ProfileRepository repos2;
    private final FriendrequestRepository repos;

    public FriendrequestService(FriendrequestRepository repos, ProfileRepository repos2,UserRepository repos3) {
        this.repos = repos;
        this.repos2 = repos2;
        this.repos3 = repos3;
    }

    public long createFriendrequest(long profileidmaker, long profileidreceiver){
        Friendrequest friendrequest1 = new Friendrequest();

        Profile maker = repos2.findById(profileidmaker).get();

        Profile receiver = repos2.findById(profileidreceiver).get();


        friendrequest1.setMaker(maker);
        friendrequest1.setReceiver(receiver);
        Friendrequest friendrequest2 = repos.save(friendrequest1);
        List<Friendrequest> friendrequests = receiver.getFriendrequests();

        friendrequests.add(friendrequest2);

        receiver.setFriendrequests(friendrequests);

        repos2.save(receiver);

        return friendrequest2.getId();

    }


    public FriendrequestDto getFriendrequestbyID(long id) {
        Optional<Friendrequest> friendrequest = repos.findById(id);
        if (friendrequest.isEmpty()) {
            throw new RecordNotFoundException("Cannot find id:" + id);
        } else {
            Friendrequest friendrequest2 = repos.findById(id).get();
            FriendrequestDto friendrequestdto = FriendrequestDto.fromFriendrequest(friendrequest2);

            return friendrequestdto;

        }
    }


    public List<FriendrequestDto>findAllFriendrequestsbyProfile(long profileid) {
        Optional<Profile> profile1 = repos2.findById(profileid);

        if (profile1.isEmpty()) {
            throw new RecordNotFoundException("Cannot find id:" + profileid);
        } else {

            Profile profile2 = repos2.findById(profileid).get();
            List<Friendrequest> friendrequests = new ArrayList<>(profile2.getFriendrequests());
            List<FriendrequestDto> friendrequestsdto = new ArrayList<>();

            for (Friendrequest friendrequest : friendrequests) {
                friendrequestsdto.add(FriendrequestDto.fromFriendrequest(friendrequest));
            }
            return friendrequestsdto;
        }
    }


    public void deleteFriendrequestbyid(long id) {
        Optional<Friendrequest> friendrequest1 = repos.findById(id);
        if (friendrequest1.isEmpty()) {
            throw new RecordNotFoundException("Cannot find id:" + id);
        } else {
            repos.deleteById(id);
        }
    }


    public void acceptFriendrequestbyID(long id) {
        Optional<Friendrequest> friendrequest1 = repos.findById(id);
        if (friendrequest1.isEmpty()) {
            throw new RecordNotFoundException("Cannot find id:" + id);
        } else {

            Friendrequest friendrequest2 = repos.findById(id).get();
            Profile receiver = friendrequest2.getReceiver();
            Profile maker = friendrequest2.getMaker();

            List<Profile> friendlist2 = maker.getFriendlist();
            List<Profile> friendlist = receiver.getFriendlist();
            friendlist.add(maker);
            friendlist2.add(receiver);
            receiver.setFriendlist(friendlist);
            repos2.save(receiver);

            maker.setFriendlist(friendlist2);
            repos2.save(maker);

        }
    }

    public List<ProfileDto>getAllFriendsbyProfileID(long id) {
        Optional<Profile>profile1 = repos2.findById(id);
        if(profile1.isEmpty()) {
            throw new RecordNotFoundException("ID can not be found");
        }else {
            Profile profile2 = repos2.findById(id).get();
            List<Profile> Friendlist = profile2.getFriendlist();
            List<ProfileDto>Friendlist2 = new ArrayList<>();
            for(Profile profile: Friendlist) {
                Friendlist2.add(ProfileDto.fromProfile(profile));
            }

            return Friendlist2;
        }
    }

    public void deleteFriendbyID(long id, long id2) {
        List<ProfileDto> Friendlist = getAllFriendsbyProfileID(id);
        List<Profile>Friendlist2 = new ArrayList<>();
        for(ProfileDto profile: Friendlist) {
            Friendlist2.add(ProfileInputDto.toProfile(profile));
        }

        Optional<Profile> profile1 = repos2.findById(id2);
        if (profile1.isEmpty()) {
            throw new RecordNotFoundException("ID can not be found");
        } else {
            Profile profile2 = repos2.findById(id2).get();
            Friendlist2.remove(profile2);
            Profile profile3 = repos2.findById(id).get();
            profile3.setFriendlist(Friendlist2);
            repos2.save(profile3);
        }
    }


}
