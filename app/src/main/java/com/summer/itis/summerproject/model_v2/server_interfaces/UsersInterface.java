package com.summer.itis.summerproject.model_v2.server_interfaces;

import com.summer.itis.summerproject.model_v2.entities.general.User;

import java.util.List;

import io.reactivex.Single;

public interface UsersInterface {
    Single<User> getUserById(String Id);

    Single<User> getCurrentUser();

    Single<List<User>> getFriends();

    Single<User> addFriend(String id);
    //TODO
}
