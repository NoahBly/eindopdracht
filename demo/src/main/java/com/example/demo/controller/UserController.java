package com.example.demo.controller;

import com.example.demo.dto.CreateUserProfileDto;
import com.example.demo.dto.UserDto;
import com.example.demo.exceptions.BadRequestException;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/users")
public class UserController {

    UserService userService;

   PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/normal")
    public ResponseEntity<UserDto> createNormalUserProfile(@RequestBody CreateUserProfileDto dto) throws Exception{

        dto.setPassword(passwordEncoder.encode(dto.password));

        long newUserid = userService.createNormalUser(dto);
        userService.addAuthority(newUserid, "ROLE_NORMAL_USER");



        return ResponseEntity.created(null).build();

    }

    @PostMapping(value = "/celebrity")
    public ResponseEntity<UserDto> createCelebrityUserProfile(@RequestBody CreateUserProfileDto dto) throws Exception{
        dto.setPassword(passwordEncoder.encode(dto.password));

        long newUserid = userService.createCelebUser(dto);
        userService.addAuthority(newUserid, "ROLE_CELEB_USER");



        return ResponseEntity.created(null).build();

    }

    @PostMapping(value = "/pageadmin")
    public ResponseEntity<UserDto> createPageAdminUserProfile(@RequestBody CreateUserProfileDto dto) throws Exception{
        dto.setPassword(passwordEncoder.encode(dto.password));

        long newUserid = userService.createPageAdminUser(dto);
        userService.addAuthority(newUserid, "ROLE_PAGE_ADMIN_USER");



        return ResponseEntity.created(null).build();

    }

    @GetMapping(value = "")
    public ResponseEntity<List<UserDto>> getUsers(){
        List<UserDto> userDtos = userService.getUsers();

        return ResponseEntity.ok().body(userDtos);
    }

    @GetMapping(value = "/id/{userid}")
    public ResponseEntity<UserDto> getUserbyID(@PathVariable("userid") long userid) throws Exception {
        UserDto optionalUser = userService.getUserbyID(userid);

        return ResponseEntity.ok().body(optionalUser);
    }

    @GetMapping(value = "/username/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable("username") String username) throws Exception {
        UserDto optionalUser = userService.getUser(username);

        return ResponseEntity.ok().body(optionalUser);
    }



    @PutMapping(value = "/{userid}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userid") long id, @RequestBody UserDto userdto) throws Exception {
       userService.updateUser(id, userdto);

       return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{userid}")
    public ResponseEntity<Object> deleteUser(@PathVariable("userid") long userid) {
        userService.deleteUser(userid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{userid}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("userid") long userid) throws Exception {
        return ResponseEntity.ok().body(userService.getAuthorities(userid));
    }

    @PostMapping(value = "/{userid}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("userid") long userid, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addAuthority(userid, authorityName);
            return ResponseEntity.noContent().build();
        }
        catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @DeleteMapping(value = "/{userid}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("userid") long id , @PathVariable("authority") String authority) throws Exception {
        userService.removeAuthority(id, authority);
        return ResponseEntity.noContent().build();
    }

}
