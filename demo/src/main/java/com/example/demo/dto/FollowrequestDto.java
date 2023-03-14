package com.example.demo.dto;

import com.example.demo.model.Followrequest;
import com.example.demo.model.Friendrequest;
import com.example.demo.model.Profile;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class FollowrequestDto {


    @NotBlank
    public long id;

    @NotBlank
    public Profile maker;

    @NotBlank
    public Profile receiver;

    public FollowrequestDto(long id, Profile maker, Profile receiver) {
        this.id = id;
        this.maker = maker;
        this.receiver = receiver;
    }

    public FollowrequestDto() {
    }

    public static FollowrequestDto fromFollowrequest(Followrequest followrequest1) {
       FollowrequestDto followrequest2 = new FollowrequestDto();
        followrequest2.id = followrequest1.getId();
        followrequest2.maker = followrequest1.getMaker();
        followrequest2.receiver = followrequest1.getReceiver();

        return followrequest2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowrequestDto that = (FollowrequestDto) o;
        return id == that.id && Objects.equals(maker, that.maker) && Objects.equals(receiver, that.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, maker, receiver);
    }
}
