package com.example.demo.dto;

import com.example.demo.model.Followrequest;
import com.example.demo.model.Friendrequest;
import com.example.demo.model.Profile;
import javax.validation.constraints.NotBlank;

public class FollowrequestDto {


    @NotBlank
    public long id;

    @NotBlank
    public Profile maker;

    @NotBlank
    public Profile receiver;



    public static FollowrequestDto fromFollowrequest(Followrequest followrequest1) {
       FollowrequestDto followrequest2 = new FollowrequestDto();

        followrequest2.maker = followrequest1.getMaker();
        followrequest2.receiver = followrequest1.getReceiver();

        return followrequest2;
    }

}
