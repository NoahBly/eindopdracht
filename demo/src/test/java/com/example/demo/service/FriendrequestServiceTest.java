package com.example.demo.service;

import com.example.demo.dto.FriendrequestDto;
import com.example.demo.dto.ProfileDto;
import com.example.demo.exceptions.RecordNotFoundException;
import com.example.demo.model.Friendrequest;
import com.example.demo.model.Profile;
import com.example.demo.model.ProfiletoProfile;
import com.example.demo.model.User;
import com.example.demo.repository.FriendrequestRepository;
import com.example.demo.repository.ProfileRepository;
import com.example.demo.repository.ProfiletoProfileRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class FriendrequestServiceTest {

    @ExtendWith(MockitoExtension.class)


    @Mock
    UserRepository repos3;
    @Mock
    ProfileRepository repos2;
    @Mock
    FriendrequestRepository repos;
    @Mock
    ProfiletoProfileRepository repos4;

    @InjectMocks
    FriendrequestService friendrequestService;

    @Captor
    ArgumentCaptor<Friendrequest> captor;

    Profile maker1;
    Profile receiver1;
    Friendrequest friendrequest2;

    User user1;

    User user2;


    @BeforeEach
    void setUp() {
       List <ProfiletoProfile> friendlist = new ArrayList<>();
        List <Friendrequest> friendrequests =new ArrayList<>();
        friendrequest2 = new Friendrequest(4,maker1,receiver1);


        maker1 = new Profile(1,"NORMAL","keesjan",null, friendlist,null,null,null,null,null,null,null, user1);

        List <ProfiletoProfile> friendlist2 = new ArrayList<>();

        receiver1 = new Profile(3,"NORMAL","jantje",null, friendlist2,null,friendrequests,null,null,null,null,null,user2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test()
    void createFriendrequest() {
        //Arrange
       long profileidmaker = 1;
       long profileidreceiver = 3;




    Friendrequest friendrequest = new Friendrequest();
        when(repos2.findById(profileidmaker)).thenReturn(Optional.ofNullable(maker1));
        when(repos2.findById(profileidreceiver)).thenReturn(Optional.ofNullable(receiver1));



    friendrequest.setMaker(maker1);
    friendrequest.setReceiver(receiver1);

   when(repos.save(any(Friendrequest.class))).thenReturn(friendrequest2);


    List<Friendrequest> friendrequests = receiver1.getFriendrequests();

    friendrequests.add(friendrequest2);

    receiver1.setFriendrequests(friendrequests);

    when(repos2.save(receiver1)).thenReturn(receiver1);

        long friendrequest2Id = friendrequestService.createFriendrequest(profileidmaker,profileidreceiver);


        //long idmaker
        //long idreceiver
        //Profile maker
        //profile receiver

        //Friendrequest friendrequest2

        assertEquals(friendrequest2.getId(),friendrequest2Id);

    }

    @Test
    void getFriendrequestbyID() {
        long profileidreceiver = 3;

        when(repos.findById(profileidreceiver)).thenReturn(Optional.ofNullable(friendrequest2));

        FriendrequestDto friendrequestdto1 = friendrequestService.getFriendrequestbyID(profileidreceiver);

        FriendrequestDto friendrequestDtocheck = new FriendrequestDto(4,maker1,receiver1);

        assertEquals(FriendrequestDto.fromFriendrequest(friendrequest2),friendrequestdto1);

    }

    @Test
    void getFriendrequestbyIdThrowsExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> friendrequestService.getFriendrequestbyID(1l));
    }


    @Test
    void findAllFriendrequestsbyProfile() {
        long profileidreceiver = 3;

        when(repos2.findById(profileidreceiver)).thenReturn(Optional.ofNullable(receiver1));

        List<FriendrequestDto> friendrequests = new ArrayList<>(friendrequestService.findAllFriendrequestsbyProfile(profileidreceiver));

        List<Friendrequest> friendrequests2 = new ArrayList<>(receiver1.getFriendrequests());
        List<FriendrequestDto> friendrequestsdto = new ArrayList<>();

        for (Friendrequest friendrequest : friendrequests2) {
            friendrequestsdto.add(FriendrequestDto.fromFriendrequest(friendrequest));
        }

        assertEquals(friendrequestsdto,friendrequests);
    }

    @Test
    void findallFriendrequestbyProfileThrowsExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> friendrequestService.findAllFriendrequestsbyProfile(1l));
    }

    @Test
    void deleteFriendrequestbyid() {
        long friendrequestid = 4;

        when(repos.findById(friendrequestid)).thenReturn(Optional.ofNullable(friendrequest2));

        friendrequestService.deleteFriendrequestbyid(friendrequest2.getId());

        verify(repos).deleteById(friendrequest2.getId());
    }


    @Test
    void deleteFriendrequestbyidThrowsExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> friendrequestService.deleteFriendrequestbyid(1l));
    }

    @Test
    void acceptFriendrequestbyID() {

    }

    @Test
    void getAllFriendsbyProfileID() {
    }

    @Test
    void deleteFriendbyID() {
    }
}