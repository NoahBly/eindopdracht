package com.example.demo.service;

import com.example.demo.Utils.RandomStringGenerator;
import com.example.demo.dto.CreateUserProfileDto;
import com.example.demo.dto.ProfileDto;
import com.example.demo.dto.ProfileInputDto;
import com.example.demo.dto.UserDto;
import com.example.demo.exceptions.RecordNotFoundException;
import com.example.demo.model.Authority;
import com.example.demo.model.Profile;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    /*autowire de juiste repository*/
    UserRepository repos;

    ProfileService profileservice;




    public UserService(UserRepository repos,ProfileService profileservice) {
        this.repos = repos;
        this.profileservice = profileservice;
    }

    public List<UserDto> getUsers() {
        List<UserDto> collection = new ArrayList<>();
        List<User> list = repos.findAll();
        for (User user : list) {
            collection.add(fromUser(user));
        }
        return collection;
    }

    public UserDto getUser(String username) throws RecordNotFoundException {
        UserDto dto = new UserDto();
        User user = repos.findByUsername(username);
        if (user!=null) {
            dto = fromUser(user);
        }else {
            throw  new RecordNotFoundException("Cannot find" + username);
        }
        return dto;
    }


    public boolean userExists(long id) {
        return repos.existsById(id);
    }

    public String createNormalUser(CreateUserProfileDto dto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        dto.setApikey(randomString);

        User newUser = new User();
        newUser.setApikey(randomString);
        newUser.setUsername(dto.username);
        newUser.setPassword(dto.password);

        newUser.setEmail(dto.email);

        Profile profile1 = profileservice.createNormalProfile(dto);

        newUser.setProfile(profile1);
        newUser.setProfileId(profile1.getId());

        repos.save(newUser);


        return newUser.getUsername();
    }

    public String createCelebUser(CreateUserProfileDto dto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        dto.setApikey(randomString);

        User newUser = new User();
        newUser.setApikey(randomString);
        newUser.setUsername(dto.username);
        newUser.setPassword(dto.password);

        newUser.setEmail(dto.email);

        Profile profile1 = profileservice.createCelebrityProfile(dto);

        newUser.setProfile(profile1);

        repos.save(newUser);


        return newUser.getUsername();
    }

    public String createPageAdminUser(CreateUserProfileDto dto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        dto.setApikey(randomString);

        User newUser = new User();
        newUser.setApikey(randomString);
        newUser.setUsername(dto.username);
        newUser.setPassword(dto.password);

        newUser.setEmail(dto.email);

        Profile profile1 = profileservice.createPageAdminProfile(dto);

        newUser.setProfile(profile1);

        repos.save(newUser);


        return newUser.getUsername();
    }

    public void deleteUser(String username) {
        repos.deleteByUsername(username);
    }

    public void updateUser(String username, UserDto newUser) throws Exception {

        //if (!repos.existsById(username)) throw new Exception();
        User user = repos.findByUsername(username);
        user.setPassword(newUser.getPassword());
        repos.save(user);
    }

    public Set<Authority> getAuthorities(String username) throws Exception {
       // if (!repos.existsById(username)) throw new Exception(username);

        User user = repos.findByUsername(username);
        UserDto userDto = fromUser(user);
        return userDto.getAuthorities();
    }

    public void addAuthority(String username, String authority) throws Exception {

        //if (!repos.existsById(username)) throw new Exception(username);
        User user = repos.findByUsername(username);
        user.addAuthority(new Authority(username, authority));
        repos.save(user);
    }

    public void removeAuthority(String username, String authority) throws Exception {
       // if (!repos.existsById(username)) throw new Exception(username);
        User user = repos.findByUsername(username);
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        repos.save(user);
    }

    public static UserDto fromUser(User user){

        var dto = new UserDto();
        dto.id = user.getId();
        dto.username = user.getUsername();
        dto.password = user.getPassword();

        dto.apikey = user.getApikey();
        dto.email = user.getEmail();
        dto.authorities = user.getAuthorities();
        dto.profile = user.getProfile();
        dto.profile_id = user.getProfileId();

        return dto;
    }

    public User toUser(UserDto userDto) {

        var user = new User();
        user.setId(userDto.id);
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());

        user.setApikey(userDto.getApikey());
        user.setEmail(userDto.getEmail());
        user.setProfileId(user.getProfileId());

        return user;
    }

}
