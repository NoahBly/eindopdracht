package com.example.demo.service;

import com.example.demo.dto.FollowrequestDto;
import com.example.demo.dto.FriendrequestDto;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    }

    @Test
    void getAllFollowersbyProfileID() {
    }

    @Test
    void deleteFollowerbyID() {
    }

    @Test
    void getAllFollowingsbyProfileID() {
    }

    @Test
    void deleteFollowingbyID() {
    }
}