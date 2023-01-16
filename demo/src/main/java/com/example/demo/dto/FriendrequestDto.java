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
    public Profile maker;

    @NotBlank
    public Profile receiver;



    public static FriendrequestDto fromFriendrequest(Friendrequest friendrequest1) {
        FriendrequestDto friendrequest2 = new FriendrequestDto();

        friendrequest2.maker = friendrequest1.getMaker();
        friendrequest2.receiver = friendrequest1.getReceiver();

        return friendrequest2;
    }
}
