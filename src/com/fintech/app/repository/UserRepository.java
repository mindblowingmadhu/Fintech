package com.fintech.app.repository;

import java.util.HashMap;
import java.util.Map;

import com.fintech.app.model.User;
import com.fintech.app.repository.interfaces.UserRepositoryInterface;
public class UserRepository implements UserRepositoryInterface {
    private final Map<String, User> userStore = new HashMap<>();

    public void save(User user) {
        userStore.put(user.getUserId(), user);
    }

    public User findById(String userId) {
        return userStore.get(userId);
    }
}