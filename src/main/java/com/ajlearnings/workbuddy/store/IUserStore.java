package com.ajlearnings.workbuddy.store;

import com.ajlearnings.workbuddy.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface IUserStore {
    User add(User user);
    User getByUsername(String username);
    User getByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User update(User user);
}
