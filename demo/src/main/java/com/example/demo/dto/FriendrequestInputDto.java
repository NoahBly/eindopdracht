package com.example.demo.dto;

import com.example.demo.model.Friendrequest;
import com.example.demo.model.Profile;
import javax.validation.constraints.NotBlank;

public class FriendrequestInputDto {


    @NotBlank
    private long id;

    @NotBlank
    private Profile maker;

    @NotBlank
    private Profile receiver;



    public static Friendrequest toFriendrequest(FriendrequestDto friendrequest1) {
        Friendrequest friendrequest2 = new Friendrequest();
        friendrequest2.setId(friendrequest1.id);
        friendrequest2.setMaker(ProfileInputDto.toProfile(friendrequest1.maker));
        friendrequest2.setReceiver(ProfileInputDto.toProfile(friendrequest1.receiver));

        return friendrequest2;
    }
}
