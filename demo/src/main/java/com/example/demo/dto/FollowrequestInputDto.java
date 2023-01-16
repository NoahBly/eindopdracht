package com.example.demo.dto;

import com.example.demo.model.Followrequest;
import com.example.demo.model.Friendrequest;
import com.example.demo.model.Profile;
import javax.validation.constraints.NotBlank;

public class FollowrequestInputDto {


    @NotBlank
    private long id;

    @NotBlank
    private Profile maker;

    @NotBlank
    private Profile receiver;



    public static Followrequest toFollowrequest(FollowrequestDto followrequest1) {
        Followrequest followrequest2 = new Followrequest();

        followrequest2.setMaker(followrequest1.maker);
        followrequest2.setReceiver(followrequest1.receiver);

        return followrequest2;
    }
}
