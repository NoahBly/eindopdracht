package com.example.demo.dto;

import com.example.demo.model.Followrequest;
import com.example.demo.model.Friendrequest;
import com.example.demo.model.Profile;
import javax.validation.constraints.NotBlank;

public class FollowrequestDto {


    @NotBlank
    public long id;

    @NotBlank
    public ProfileDto maker;

    @NotBlank
    public ProfileDto receiver;



    public static FollowrequestDto fromFollowrequest(Followrequest followrequest1) {
       FollowrequestDto followrequest2 = new FollowrequestDto();
        followrequest2.id = followrequest1.getId();
        followrequest2.maker = ProfileDto.fromProfile(followrequest1.getMaker());
        followrequest2.receiver = ProfileDto.fromProfile(followrequest1.getReceiver());

        return followrequest2;
    }

}
