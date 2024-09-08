package com.ajlearnings.workbuddy.store;

import com.ajlearnings.workbuddy.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IUserStore {
    Optional<User> getByUserName(String userName);
    User add(User user);
}
