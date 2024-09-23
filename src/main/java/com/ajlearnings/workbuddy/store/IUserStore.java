package com.ajlearnings.workbuddy.store;

import com.ajlearnings.workbuddy.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface IUserStore {
    User add(User user);
    User getByUserName(String userName);
    User getByEmail(String email);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
}
