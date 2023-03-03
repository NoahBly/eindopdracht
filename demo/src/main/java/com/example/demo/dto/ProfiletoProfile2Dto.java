package com.example.demo.dto;
import com.example.demo.model.Profile;
import com.example.demo.model.ProfiletoProfile;
import com.example.demo.model.ProfiletoProfile2;

import javax.validation.constraints.NotBlank;

public class ProfiletoProfile2Dto {

    @NotBlank
    public long id;

    @NotBlank
    public ProfileDto user;

    @NotBlank
    public ProfileDto friend;

    public static ProfiletoProfile2Dto fromP2P(ProfiletoProfile2 P2P) {
        ProfiletoProfile2Dto P2P1 = new ProfiletoProfile2Dto();

        P2P1.id = P2P.getId();
        P2P1.user = ProfileDto.fromProfile(P2P.getUser());
        P2P1.friend = ProfileDto.fromProfile(P2P.getFriend());

        return P2P1;
    }

    public static ProfiletoProfile2 toP2P(ProfiletoProfile2Dto P2P) {
        ProfiletoProfile2 P2P1 = new ProfiletoProfile2();
        P2P1.setId(P2P.id);
        P2P1.setUser(ProfileInputDto.toProfile(P2P.user));
        P2P1.setFriend(ProfileInputDto.toProfile(P2P.friend));

        return P2P1;
    }


}