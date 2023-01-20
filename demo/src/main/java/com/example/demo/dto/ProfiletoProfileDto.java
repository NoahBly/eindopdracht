package com.example.demo.dto;

import com.example.demo.model.Post;
import com.example.demo.model.Profile;
import com.example.demo.model.ProfiletoProfile;

import javax.validation.constraints.NotBlank;

public class ProfiletoProfileDto {

    @NotBlank
    public long id;

    @NotBlank
    public Profile user;

    @NotBlank
    public Profile friend;

    public static ProfiletoProfileDto fromP2P(ProfiletoProfile P2P) {
        ProfiletoProfileDto P2P1 = new ProfiletoProfileDto();

        P2P1.id = P2P.getId();
        P2P1.user = P2P.getUser();
        P2P1.friend = P2P.getFriend();

        return P2P1;
    }

    public static ProfiletoProfile toP2P(ProfiletoProfileDto P2P) {
        ProfiletoProfile P2P1 = new ProfiletoProfile();
        P2P1.setId(P2P.id);
        P2P1.setUser(P2P.user);
        P2P1.setFriend(P2P.friend);

        return P2P1;
    }


}
