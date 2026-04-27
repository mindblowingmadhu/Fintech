package com.fintech.app.repository.interfaces;

import com.fintech.app.model.User;

public interface UserRepositoryInterface {

    void save(User user);

    User findById(String userId);
}