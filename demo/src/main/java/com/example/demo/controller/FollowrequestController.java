package com.example.demo.controller;


import com.example.demo.dto.FollowrequestDto;
import com.example.demo.dto.FriendrequestDto;
import com.example.demo.dto.IdinputDto;
import com.example.demo.dto.ProfileDto;
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

    @PostMapping(value = "")
    public ResponseEntity<Object> createFollowRequest(@RequestBody long idmaker, @RequestBody IdinputDto idreceiver) throws Exception{

        long postid = service.createFollowrequest(idmaker, idreceiver.id);

        return new ResponseEntity<>(postid, HttpStatus.CREATED);

    }



    @GetMapping(value = "/{followrequestid}")
    public ResponseEntity<FollowrequestDto> getFollowrequestbyID(@PathVariable("followrequestid") long id) throws Exception {
        FollowrequestDto optionalFollowrequest = service.getFollowrequestbyID(id);

        return ResponseEntity.ok().body(optionalFollowrequest);
    }



    @GetMapping(value = "/{profileid}")
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

    @GetMapping(value = "/{profileid}/followers")
    public ResponseEntity<List<ProfileDto>> getAllFollowersbyProfileID(@PathVariable("profileid") long profileid){
        List<ProfileDto> FollowprofileDtos = service.getAllFollowersbyProfileID(profileid);

        return ResponseEntity.ok().body(FollowprofileDtos);
    }


    @DeleteMapping ("/{profileid}/followers/{profilefollowerid}")
    public ResponseEntity<Object> deleteFollowerByID(@PathVariable("profileid") long profileid,@PathVariable("profilefollowerid") long profilefollowerid) {
        service.deleteFollowerbyID(profileid,profilefollowerid);
        return  ResponseEntity.noContent().build();
    }



}
