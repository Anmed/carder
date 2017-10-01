package com.anmed.storage.service;

import com.anmed.storage.model.User;

public interface UserService extends GeneralCRUDservice<User> {

    User findByUsername(String username);
}
