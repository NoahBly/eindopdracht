package com.example.demo.controller;

import com.example.demo.dto.FriendrequestDto;
import com.example.demo.dto.IdinputDto;
import com.example.demo.dto.PostDto;
import com.example.demo.dto.ProfileDto;
import com.example.demo.model.Profile;
import com.example.demo.service.FriendrequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/friendrequests")
public class FriendrequestController {

    FriendrequestService service;

    public FriendrequestController(FriendrequestService service) {
        this.service = service;
    }

    @PostMapping(value = "")
    public ResponseEntity<Object> createFriendRequest(@RequestBody long idmaker, @RequestBody IdinputDto idreceiver) throws Exception{

        long postid = service.createFriendrequest(idmaker, idreceiver.id);

        return new ResponseEntity<>(postid, HttpStatus.CREATED);

    }



    @GetMapping(value = "/{friendrequestid}")
    public ResponseEntity<FriendrequestDto> getFriendrequestbyID(@PathVariable("friendrequestid") long id) throws Exception {
       FriendrequestDto optionalFriendrequest = service.getFriendrequestbyID(id);

        return ResponseEntity.ok().body(optionalFriendrequest);
    }



    @GetMapping(value = "/{profileid}")
    public ResponseEntity<List<FriendrequestDto>> getAllFriendrequestsbyProfileID(@PathVariable("profileid") long profileid){
        List<FriendrequestDto> friendrequestDtos = service.findAllFriendrequestsbyProfile(profileid);

        return ResponseEntity.ok().body(friendrequestDtos);
    }


    @DeleteMapping(value = "/{friendrequestid}")
    public ResponseEntity<Object> deletefriendrequestbyID(@PathVariable("friendrequestid") long id) {
        service.deleteFriendrequestbyid(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{friendrequestid}")
    public ResponseEntity<Object> acceptFriendrequestbyID(@PathVariable("friendrequestid") long friendrequestid) {
        service.acceptFriendrequestbyID(friendrequestid);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{profileid}/friends")
    public ResponseEntity<List<ProfileDto>> getAllFriendsbyProfileID(@PathVariable("profileid") long profileid){
        List<ProfileDto> FriendprofileDtos = service.getAllFriendsbyProfileID(profileid);

        return ResponseEntity.ok().body(FriendprofileDtos);
    }



    @DeleteMapping ("/{profileid}/friends/{profilefriendid}")
    public ResponseEntity<Object> deleteFriendByID(@PathVariable("profileid") long profileid,@PathVariable("profilefriendid") long profilefriendid) {
        service.deleteFriendbyID(profileid,profilefriendid);
        return  ResponseEntity.noContent().build();
    }


}
