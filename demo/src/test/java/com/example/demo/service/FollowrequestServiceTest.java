package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.exceptions.RecordNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FollowrequestServiceTest {

    @ExtendWith(MockitoExtension.class)


    @Mock
    UserRepository repos3;
    @Mock
    ProfileRepository repos2;
    @Mock
    FollowrequestRepository repos;
    @Mock
    ProfiletoProfile2Repository repos4;
    @Mock
    ProfiletoProfile3Repository repos5;

    @InjectMocks
    FollowrequestService followrequestService;

    @Captor
    ArgumentCaptor<Profile> makercaptor;

    Profile maker1;
    Profile receiver1;
    Followrequest followrequest2;

    User user1;

    User user2;

    ProfiletoProfile3 ptp1;

    ProfiletoProfile2 ptp2;

    @BeforeEach
    void setUp() {
        List<ProfiletoProfile2> followerlist = new ArrayList<>();
        List<ProfiletoProfile3> followinglist = new ArrayList<>();

        List <Followrequest> followrequests =new ArrayList<>();
        List <Followrequest> followrequests2 =new ArrayList<>();


        maker1 = new Profile(1,"NORMAL","keesjan",null, null,followrequests2,null,followerlist,followinglist,null,null,null, user1);

        List<ProfiletoProfile2> followerlist2 = new ArrayList<>();
        List<ProfiletoProfile3> followinglist2 = new ArrayList<>();

        receiver1 = new Profile(3,"NORMAL","jantje",null, null,followrequests,null,followerlist2,followinglist2,null,null,null,user2);
        followrequest2 = new Followrequest(4,maker1,receiver1);
        ptp1 = new ProfiletoProfile3(5,maker1,receiver1,ptp2);
        ptp2 = new ProfiletoProfile2(6,receiver1,maker1,ptp1);


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createFollowrequest() {
        long profileidmaker = 1;
        long profileidreceiver = 3;




        Followrequest followrequest = new Followrequest();
        when(repos2.findById(profileidmaker)).thenReturn(Optional.ofNullable(maker1));
        when(repos2.findById(profileidreceiver)).thenReturn(Optional.ofNullable(receiver1));



        followrequest.setMaker(maker1);
        followrequest.setReceiver(receiver1);

        when(repos.save(any(Followrequest.class))).thenReturn(followrequest2);


        List<Followrequest> followrequests = receiver1.getFollowrequests();

        followrequests.add(followrequest2);

        receiver1.setFollowrequests(followrequests);

        when(repos2.save(receiver1)).thenReturn(receiver1);

        long followrequest2Id = followrequestService.createFollowrequest(profileidmaker,profileidreceiver);


        //long idmaker
        //long idreceiver
        //Profile maker
        //profile receiver

        //Friendrequest friendrequest2

        assertEquals(followrequest2.getId(),followrequest2Id);
    }

    @Test
    void getFollowrequestbyID() {
        long profileidreceiver = 3;

        when(repos.findById(profileidreceiver)).thenReturn(Optional.ofNullable(followrequest2));

        FollowrequestDto followrequestdto1 = followrequestService.getFollowrequestbyID(profileidreceiver);

        FollowrequestDto followrequestDtocheck = new FollowrequestDto(4,maker1,receiver1);

        assertEquals(FollowrequestDto.fromFollowrequest(followrequest2),followrequestdto1);


    }

    @Test
    void getFollowrequestbyIDThrowsExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> followrequestService.getFollowrequestbyID(1l));
    }


    @Test
    void findAllFollowrequestsbyProfile() {

        long profileidreceiver = 3;

        when(repos2.findById(profileidreceiver)).thenReturn(Optional.ofNullable(receiver1));

        List<FollowrequestDto> followrequests = new ArrayList<>(followrequestService.findAllFollowrequestsbyProfile(profileidreceiver));

        List<Followrequest> followrequests2 = new ArrayList<>(receiver1.getFollowrequests());
        List<FollowrequestDto> followrequestsdto = new ArrayList<>();

        for (Followrequest followrequest : followrequests2) {
            followrequestsdto.add(FollowrequestDto.fromFollowrequest(followrequest));
        }

        assertEquals(followrequestsdto,followrequests);
    }

    @Test
    void findAllFollowrequestsbyProfileThrowsExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> followrequestService.findAllFollowrequestsbyProfile(1l));
    }


    @Test
    void deleteFollowrequestbyid() {

        long followrequestid = 4;

        when(repos.findById(followrequestid)).thenReturn(Optional.ofNullable(followrequest2));

        followrequestService.deleteFollowrequestbyid(followrequest2.getId());

        verify(repos).deleteById(followrequest2.getId());
    }

    @Test
    void deleteFollowrequestbyidThrowsExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> followrequestService.deleteFollowrequestbyid(1l));
    }


    @Test
    void acceptFollowrequestbyID() {

        long followrequestid = 4;


        when(repos.findById(followrequestid)).thenReturn(Optional.ofNullable(followrequest2));
        lenient().when(repos5.save(ptp1)).thenReturn(ptp1);
        lenient().when(repos4.save(ptp2)).thenReturn(ptp2);

        List<ProfiletoProfile2> followerslist = receiver1.getFollowerslist();

        followerslist.add(ptp2);

        receiver1.setFollowerslist(followerslist);

        when(repos2.save(receiver1)).thenReturn(receiver1);


        List<ProfiletoProfile3> followingslist = maker1.getFollowinglist();

        followingslist.add(ptp1);

        maker1.setFollowinglist(followingslist);

        when(repos2.save(maker1)).thenReturn(maker1);

        followrequestService.acceptFollowrequestbyID(followrequestid);
        verify(repos2, times(2)).save(makercaptor.capture());

        List<Profile> capturedProfiles = makercaptor.getAllValues();
        assertEquals(receiver1.getFollowerslist(), capturedProfiles.get(0).getFollowerslist());
        assertEquals(maker1.getFollowinglist(), capturedProfiles.get(1).getFollowinglist());

    }

    @Test
    void acceptFollowrequestbyIDThrowsExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> followrequestService.acceptFollowrequestbyID(1l));
    }
    @Test
    void getAllFollowersbyProfileID() {
        long profileidreceiver = 3;

        when(repos2.findById(profileidreceiver)).thenReturn(Optional.ofNullable(receiver1));

        List<ProfiletoProfile2Dto> followerslist = new ArrayList<>(followrequestService.getAllFollowersbyProfileID(profileidreceiver));

        List<ProfiletoProfile2> followerslist2 = new ArrayList<>(receiver1.getFollowerslist());
        List<ProfiletoProfile2Dto> followerslist2dto = new ArrayList<>();

        for (ProfiletoProfile2 p2p : followerslist2) {
            followerslist2dto.add(ProfiletoProfile2Dto.fromP2P(p2p));
        }

        assertEquals(followerslist2dto,followerslist);
    }

    @Test
    void getAllFollowersbyProfileIDThrowsExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> followrequestService.getAllFollowersbyProfileID(1l));
    }
    @Test
    void deleteFollowerbyID() {
        long p2pid = ptp2.getId();
        long profileid = receiver1.getId();

        when(repos4.findById(p2pid)).thenReturn(Optional.ofNullable(ptp2));

        followrequestService.deleteFollowerbyID(profileid, ptp2.getId());

        verify(repos4).delete(ptp2);

    }

    @Test
    void deleteFollowerbyIDThrowsExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> followrequestService.deleteFollowerbyID(1l,1l));
    }

    @Test
    void getAllFollowingsbyProfileID() {

        long profileidreceiver = 1;

        when(repos2.findById(profileidreceiver)).thenReturn(Optional.ofNullable(maker1));

        List<ProfiletoProfile3Dto> followingsslist = new ArrayList<>(followrequestService.getAllFollowingsbyProfileID(profileidreceiver));

        List<ProfiletoProfile3> followingslist2 = new ArrayList<>(maker1.getFollowinglist());
        List<ProfiletoProfile3Dto> followingslist3dto = new ArrayList<>();

        for (ProfiletoProfile3 p2p : followingslist2) {
            followingslist3dto.add(ProfiletoProfile3Dto.fromP2P(p2p));
        }

        assertEquals(followingslist3dto,followingsslist);
    }

    @Test
    void getAllFollowingsbyProfileIDThrowsExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> followrequestService.getAllFollowingsbyProfileID(1l));
    }

    @Test
    void deleteFollowingbyID() {

        long p2pid = ptp1.getId();
        long profileid = maker1.getId();

        when(repos5.findById(p2pid)).thenReturn(Optional.ofNullable(ptp1));

        followrequestService.deleteFollowingbyID(profileid, ptp1.getId());

        verify(repos5).delete(ptp1);
    }

    @Test
    void deleteFollowingbyIDThrowsExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> followrequestService.deleteFollowingbyID(1l,1l));
    }
}