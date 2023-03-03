package com.example.demo.dto;

import com.example.demo.model.Friendrequest;
import com.example.demo.model.Post;
import com.example.demo.model.Profile;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

public class FriendrequestDto {

    @NotBlank
    public long id;

    @NotBlank
    public ProfileDto maker;

    @NotBlank
    public ProfileDto receiver;



    public static FriendrequestDto fromFriendrequest(Friendrequest friendrequest1) {
        FriendrequestDto friendrequest2 = new FriendrequestDto();
        friendrequest2.id = friendrequest1.getId();
        friendrequest2.maker = ProfileDto.fromProfile(friendrequest1.getMaker());
        friendrequest2.receiver =ProfileDto.fromProfile(friendrequest1.getReceiver());

        return friendrequest2;
    }
}
