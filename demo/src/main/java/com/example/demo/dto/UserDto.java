package com.example.demo.dto;

import com.example.demo.model.Authority;
import com.example.demo.model.Profile;
import com.example.demo.model.User;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Set;

public class UserDto {

    public long id;
    public String username;
    public String password;
    public Boolean enabled;
    public String apikey;
    public String email;

    public long profile_id;

    public UserDto(long id, String username, String password, Boolean enabled, String apikey, String email, long profile_id, Set<Authority> authorities, ProfileDto profile) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.apikey = apikey;
        this.email = email;
        this.profile_id = profile_id;
        this.authorities = authorities;
        this.profile = profile;
    }

    public UserDto() {
    }

    @JsonSerialize
    public Set<Authority> authorities;

    public ProfileDto profile;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getApikey() {
        return apikey;
    }

    public String getEmail() {
        return email;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public ProfileDto getProfile() {
        return profile;
    }

    public void setProfile(ProfileDto profile) {
        this.profile = profile;
    }

    public long getProfileId() {
        return profile_id;
    }

    public void setProfileId(long profile_id) {
        this.profile_id = profile_id;
    }

    public static UserDto fromUser(User user) {
        UserDto dto = new UserDto();
        dto.id = user.getId();
        dto.apikey = user.getApikey();
        dto.authorities = user.getAuthorities();
        dto.email = user.getEmail();
        dto.password = user.getPassword();
        dto.username = user.getUsername();
        dto.profile_id = user.getProfileId();
        dto.profile = ProfileDto.fromProfile(user.getProfile());

        return dto;
    }


    public static User toUser(UserDto user) {
        User user1 = new User();
        user1.setId(user.getId()) ;
        user1.setApikey(user.getApikey());
        user1.setAuthorities(user.getAuthorities());
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        user1.setUsername(user.getUsername());
        user1.setProfileId( user.getProfileId());
        user1.setProfile(ProfileInputDto.toProfile(user.getProfile()));

        return user1;
    }
}
