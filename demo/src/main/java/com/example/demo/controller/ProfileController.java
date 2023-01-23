package com.example.demo.controller;

import com.example.demo.dto.CreateUserProfileDto;
import com.example.demo.dto.ProfileDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/profiles")
public class ProfileController {

    ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }


    @PostMapping(value = "/{profileid}/addProfileImage")
    public ResponseEntity<String> addProfileImage(@RequestParam("file")MultipartFile file, @PathVariable("profileid") long id) throws Exception{
        String filename = service.addProfileimage(id,file);


        return ResponseEntity.created(null).build();

    }


    @GetMapping(value = "/{profileid}")
    public ResponseEntity<ProfileDto> getProfilebyID(@PathVariable("profileid") long id) throws Exception {
       ProfileDto optionalProfile = service.getProfilebyID(id);

        return ResponseEntity.ok().body(optionalProfile);
    }


    @GetMapping(value = "/user/{userid}")
    public ResponseEntity<ProfileDto> getProfilebyUserID(@PathVariable("userid") long id) throws Exception {
        ProfileDto optionalProfile = service.getProfilebyUserID(id);

        return ResponseEntity.ok().body(optionalProfile);
    }


    @GetMapping(value = "")
    public ResponseEntity<List<ProfileDto>> getAllProfiles(){
        List<ProfileDto> profileDtos = service.findAllProfiles();

        return ResponseEntity.ok().body(profileDtos);
    }

    @GetMapping(value = "/profile/{profilename}")
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
