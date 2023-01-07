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
    @JsonSerialize
    public Set<Authority> authorities;

    public Profile profile;

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

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public static UserDto fromUser(User user) {
        UserDto dto = new UserDto();
        dto.id = user.getId();
        dto.apikey = user.getApikey();
        dto.authorities = user.getAuthorities();
        dto.email = user.getEmail();
        dto.password = user.getPassword();
        dto.username = user.getUsername();
        dto.enabled = user.isEnabled();

        return dto;
    }
}
