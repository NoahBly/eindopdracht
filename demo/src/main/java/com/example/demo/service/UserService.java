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
import com.example.demo.repository.ProfileRepository;
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
    ProfileRepository repos2;
    ProfileService profileservice;




    public UserService(UserRepository repos,ProfileService profileservice,ProfileRepository repos2) {
        this.repos = repos;
        this.profileservice = profileservice;
        this.repos2 = repos2;
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

    public UserDto getUserbyID(long id) throws RecordNotFoundException {
        UserDto dto = new UserDto();
        Optional<User> user = repos.findById(id);
        if (user.isPresent()) {
            User user1 = repos.findById(id).get();
            dto = fromUser(user1);
        }else {
            throw  new RecordNotFoundException("Cannot find" + id);
        }
        return dto;
    }



    public boolean userExists(long id) {
        return repos.existsById(id);
    }

    public long createNormalUser(CreateUserProfileDto dto) {
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

        User newuser2 = repos.save(newUser);
        profile1.setUser(newuser2);
        repos2.save(profile1);


        return newUser.getId();
    }

    public long createCelebUser(CreateUserProfileDto dto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        dto.setApikey(randomString);

        User newUser = new User();
        newUser.setApikey(randomString);
        newUser.setUsername(dto.username);
        newUser.setPassword(dto.password);

        newUser.setEmail(dto.email);

        Profile profile1 = profileservice.createCelebrityProfile(dto);

        newUser.setProfile(profile1);
        newUser.setProfileId(profile1.getId());
        User newuser2 = repos.save(newUser);
        profile1.setUser(newuser2);
       repos2.save(profile1);

        return newUser.getId();
    }

    public long createPageAdminUser(CreateUserProfileDto dto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        dto.setApikey(randomString);

        User newUser = new User();
        newUser.setApikey(randomString);
        newUser.setUsername(dto.username);
        newUser.setPassword(dto.password);

        newUser.setEmail(dto.email);

        Profile profile1 = profileservice.createPageAdminProfile(dto);


        newUser.setProfile(profile1);
        newUser.setProfileId(profile1.getId());

       User newuser2 = repos.save(newUser);
       profile1.setUser(newuser2);
       repos2.save(profile1);

        return newUser.getId();
    }

    public void deleteUser(long id) {
        repos.deleteById(id);
    }

    public void updateUser(long id, UserDto newUser) throws Exception {

        //if (!repos.existsById(username)) throw new Exception();
        User user = repos.findById(id).get();
        user.setPassword(newUser.getPassword());
        user.setEmail(newUser.getEmail());
        repos.save(user);
    }

    public Set<Authority> getAuthorities(long id) throws Exception {
       // if (!repos.existsById(username)) throw new Exception(username);

        User user = repos.findById(id).get();
        UserDto userDto = fromUser(user);
        return userDto.getAuthorities();
    }

    public void addAuthority(long id, String authority) throws Exception {

        //if (!repos.existsById(username)) throw new Exception(username);
        User user = repos.findById(id).get();
        String username = user.getUsername();
        user.addAuthority(new Authority(username, authority));
        repos.save(user);
    }

    public void removeAuthority(long id, String authority) throws Exception {
       // if (!repos.existsById(username)) throw new Exception(username);
        User user = repos.findById(id).get();
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
