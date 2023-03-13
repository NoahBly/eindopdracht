package com.example.demo.dto;

import com.example.demo.model.Friendrequest;
import com.example.demo.model.Post;
import com.example.demo.model.Profile;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class FriendrequestDto {

    @NotBlank
    public long id;

    @NotBlank
    public Profile maker;

    @NotBlank
    public Profile receiver;

    public FriendrequestDto(long id, Profile maker, Profile receiver) {
        this.id = id;
        this.maker = maker;
        this.receiver = receiver;
    }

    public FriendrequestDto() {
    }

    public static FriendrequestDto fromFriendrequest(Friendrequest friendrequest1) {
        FriendrequestDto friendrequest2 = new FriendrequestDto();
        friendrequest2.id = friendrequest1.getId();
        friendrequest2.maker = friendrequest1.getMaker();
        friendrequest2.receiver = friendrequest1.getReceiver();

        return friendrequest2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendrequestDto that = (FriendrequestDto) o;
        return id == that.id && Objects.equals(maker, that.maker) && Objects.equals(receiver, that.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, maker, receiver);
    }
}
