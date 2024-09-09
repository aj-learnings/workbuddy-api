package com.ajlearnings.workbuddy.store;

import com.ajlearnings.workbuddy.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IUserStore {
    User add(User user);
    Optional<User> getByUserName(String userName);
    Optional<User> getByEmail(String email);
}
