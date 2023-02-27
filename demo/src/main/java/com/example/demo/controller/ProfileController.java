package com.example.demo.controller;

import com.example.demo.dto.CreateUserProfileDto;
import com.example.demo.dto.ProfileDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.ProfileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/profiles")
public class ProfileController {

    ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }


    @PostMapping(value = "/{profileid}/addProfileImage",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addProfileImage(@RequestParam("file")MultipartFile file, @PathVariable("profileid") Long id) throws Exception{
        String filename = service.addProfileimage(id,file);


        return ResponseEntity.created(null).build();

    }


    @GetMapping(value = "/{profileid}")
    public ResponseEntity<ProfileDto> getProfilebyID(@PathVariable("profileid") long id) throws Exception {
       ProfileDto optionalProfile = service.getProfilebyID(id);

        return ResponseEntity.ok().body(optionalProfile);
    }

    @GetMapping("/download/{profileid}")
    ResponseEntity<Resource> Findprofileimagebyprofileid(@PathVariable long profileid, HttpServletRequest request) {

        Resource resource = service.downLoadFile(profileid);

//        this mediaType decides witch type you accept if you only accept 1 type
//        MediaType contentType = MediaType.IMAGE_JPEG;
//        this is going to accept multiple types
        String mimeType;

        try{
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

//        for download attachment use next line
//        return ResponseEntity.ok().contentType(contentType).header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + resource.getFilename()).body(resource);
//        for showing image in browser
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
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
