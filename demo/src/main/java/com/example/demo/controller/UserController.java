package com.example.demo.controller;

import com.example.demo.dto.CreateUserProfileDto;
import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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

        String newUsername = userService.createNormalUser(dto);
        userService.addAuthority(newUsername, "ROLE_NORMAL_USER");



        return ResponseEntity.created(null).build();

    }

    @PostMapping(value = "/celebrity")
    public ResponseEntity<UserDto> createCelebrityUserProfile(@RequestBody CreateUserProfileDto dto) throws Exception{
        dto.setPassword(passwordEncoder.encode(dto.password));

        String newUsername = userService.createCelebUser(dto);
        userService.addAuthority(newUsername, "ROLE_CELEB_USER");



        return ResponseEntity.created(null).build();

    }

    @PostMapping(value = "/pageadmin")
    public ResponseEntity<UserDto> createPageAdminUserProfile(@RequestBody CreateUserProfileDto dto) throws Exception{
        dto.setPassword(passwordEncoder.encode(dto.password));

        String newUsername = userService.createPageAdminUser(dto);
        userService.addAuthority(newUsername, "ROLE_PAGE_ADMIN_USER");



        return ResponseEntity.created(null).build();

    }




}
