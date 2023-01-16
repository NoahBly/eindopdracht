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

        String newUsername = userService.createNormalUser(dto);
        userService.addAuthority(newUsername, "ROLE_NORMAL_USER");



        return ResponseEntity.created(null).build();

    }

    @PostMapping(value = "/celebrity")
    public ResponseEntity<UserDto> createCelebrityUserProfile(@RequestParam("userprofile") CreateUserProfileDto dto) throws Exception{
        dto.setPassword(passwordEncoder.encode(dto.password));

        String newUsername = userService.createCelebUser(dto);
        userService.addAuthority(newUsername, "ROLE_CELEB_USER");



        return ResponseEntity.created(null).build();

    }

    @PostMapping(value = "/pageadmin")
    public ResponseEntity<UserDto> createPageAdminUserProfile(@RequestParam("userprofile") CreateUserProfileDto dto) throws Exception{
        dto.setPassword(passwordEncoder.encode(dto.password));

        String newUsername = userService.createPageAdminUser(dto);
        userService.addAuthority(newUsername, "ROLE_PAGE_ADMIN_USER");



        return ResponseEntity.created(null).build();

    }

    @GetMapping(value = "")
    public ResponseEntity<List<UserDto>> getUsers(){
        List<UserDto> userDtos = userService.getUsers();

        return ResponseEntity.ok().body(userDtos);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable("username") String username) throws Exception {
        UserDto optionalUser = userService.getUser(username);

        return ResponseEntity.ok().body(optionalUser);
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("username") String username, @RequestBody UserDto userdto) throws Exception {
       userService.updateUser(username, userdto);

       return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) throws Exception {
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }

    @PostMapping(value = "/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addAuthority(username, authorityName);
            return ResponseEntity.noContent().build();
        }
        catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @DeleteMapping(value = "/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) throws Exception {
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }

}
