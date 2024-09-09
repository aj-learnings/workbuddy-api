package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.entity.User;
import com.ajlearnings.workbuddy.model.request.CreateUserRequest;
import com.ajlearnings.workbuddy.model.response.UserResponse;
import com.ajlearnings.workbuddy.store.IUserStore;
import com.ajlearnings.workbuddy.translator.UserTranslator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "user")
@Slf4j
public class UserService implements IUserService {

    private final IUserStore userStore;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(IUserStore userStore) {
        this.userStore = userStore;
    }

    @Override
    public UserResponse addUser(CreateUserRequest createUserRequest) {
        var newUser = UserTranslator.ToEntity(createUserRequest);
        newUser.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        var createdUser = userStore.add(newUser);
        return UserTranslator.ToResponse(createdUser);
    }

    @Override
    @Cacheable(key = "#userNameorEmail")
    public User getUserByUserNameorEmail(String userNameorEmail) {
        var optionalUser = userStore.getByUserName(userNameorEmail);
        if (optionalUser.isEmpty()) {
            optionalUser = userStore.getByEmail(userNameorEmail);
        }
        return optionalUser.orElse(null);
    }
}
