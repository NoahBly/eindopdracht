package com.example.demo.controller;


import com.example.demo.dto.*;
import com.example.demo.service.FollowrequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/followrequests")
public class FollowrequestController {

    FollowrequestService service;

    public FollowrequestController(FollowrequestService service) {
        this.service = service;
    }

    @PostMapping(value = "/create/{makerid}/{receiverid}")
    public ResponseEntity<Object> createFollowRequest(@PathVariable("makerid") long idmaker, @PathVariable("receiverid") long idreceiver) throws Exception{

        long postid = service.createFollowrequest(idmaker, idreceiver);

        return new ResponseEntity<>(postid, HttpStatus.CREATED);

    }



    @GetMapping(value = "/{followrequestid}")
    public ResponseEntity<FollowrequestDto> getFollowrequestbyID(@PathVariable("followrequestid") long id) throws Exception {
        FollowrequestDto optionalFollowrequest = service.getFollowrequestbyID(id);

        return ResponseEntity.ok().body(optionalFollowrequest);
    }



    @GetMapping(value = "/profile/{profileid}")
    public ResponseEntity<List<FollowrequestDto>> getAllFollowrequestsbyProfileID(@PathVariable("profileid") long profileid){
        List<FollowrequestDto> followrequestDtos = service.findAllFollowrequestsbyProfile(profileid);

        return ResponseEntity.ok().body(followrequestDtos);
    }


    @DeleteMapping(value = "/{followrequestid}")
    public ResponseEntity<Object> deletefollowrequestbyID(@PathVariable("followrequestid") long id) {
        service.deleteFollowrequestbyid(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{followrequestid}")
    public ResponseEntity<Object> acceptFollowrequestbyID(@PathVariable long followrequestid) {
        service.acceptFollowrequestbyID(followrequestid);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/profile/{profileid}/followers")
    public ResponseEntity<List<ProfiletoProfile2Dto>> getAllFollowersbyProfileID(@PathVariable("profileid") long profileid){
        List<ProfiletoProfile2Dto> FollowprofileDtos = service.getAllFollowersbyProfileID(profileid);

        return ResponseEntity.ok().body(FollowprofileDtos);
    }

    @GetMapping(value = "/profile/{profileid}/followings")
    public ResponseEntity<List<ProfiletoProfile3Dto>> getAllFollowingsbyProfileID(@PathVariable("profileid") long profileid){
        List<ProfiletoProfile3Dto> FollowingprofileDtos = service.getAllFollowingsbyProfileID(profileid);

        return ResponseEntity.ok().body(FollowingprofileDtos);
    }



    @DeleteMapping ("/profile/{profileid}/followers/{profilefollowerid}")
    public ResponseEntity<Object> deleteFollowerByID(@PathVariable("profileid") long profileid,@PathVariable("profilefollowerid") long profilefollowerid) {
        service.deleteFollowerbyID(profileid,profilefollowerid);
        return  ResponseEntity.noContent().build();
    }


    @DeleteMapping ("/profile/{profileid}/following/{profilefollowingid}")
    public ResponseEntity<Object> deleteFollowingByID(@PathVariable("profileid") long profileid,@PathVariable("profilefollowingid") long profilefollowingid) {
        service.deleteFollowingbyID(profileid,profilefollowingid);
        return  ResponseEntity.noContent().build();
    }


}
