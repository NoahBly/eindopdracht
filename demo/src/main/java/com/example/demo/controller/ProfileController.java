package com.example.demo.controller;

import com.example.demo.dto.ProfileDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/profiles")
public class ProfileController {

    ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }


    @GetMapping(value = "/{profileid}")
    public ResponseEntity<ProfileDto> getProfilebyID(@PathVariable("profileid") long id) throws Exception {
       ProfileDto optionalProfile = service.getProfilebyID(id);

        return ResponseEntity.ok().body(optionalProfile);
    }


    @GetMapping(value = "/{userid}")
    public ResponseEntity<ProfileDto> getProfilebyUserID(@PathVariable("userid") long id) throws Exception {
        ProfileDto optionalProfile = service.getProfilebyUserID(id);

        return ResponseEntity.ok().body(optionalProfile);
    }


    @GetMapping(value = "")
    public ResponseEntity<List<ProfileDto>> getAllProfiles(){
        List<ProfileDto> profileDtos = service.findAllProfiles();

        return ResponseEntity.ok().body(profileDtos);
    }

    @GetMapping(value = "/{profilename}")
    public ResponseEntity<List<ProfileDto>> getAllProfilesbyName(@PathVariable("profilename") String profilename) throws Exception{
        List<ProfileDto> profileDtos = service.findAllProfilesbyname(profilename);

        return ResponseEntity.ok().body(profileDtos);
    }

    @DeleteMapping(value = "/{profileid}")
    public ResponseEntity<Object> deleteProfile(@PathVariable("profileid") long id) {
        service.deleteProfilebyID(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{profileid}")
    public ResponseEntity<UserDto> updateProfile(@PathVariable("profileid") long id, @RequestBody ProfileDto profiledto) throws Exception {
       service.updateProfile(id, profiledto);

        return ResponseEntity.noContent().build();
    }

}
