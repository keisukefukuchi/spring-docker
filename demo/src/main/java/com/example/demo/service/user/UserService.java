package com.example.demo.service.user;

// UserService.java
import com.example.demo.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(UUID id);

    void saveUser(User user);

    void deleteUser(UUID id);
}

