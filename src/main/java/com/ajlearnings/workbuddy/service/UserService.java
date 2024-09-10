package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.entity.User;
import com.ajlearnings.workbuddy.exception.UserAlreadyExistException;
import com.ajlearnings.workbuddy.model.request.CreateUserRequest;
import com.ajlearnings.workbuddy.model.response.UserResponse;
import com.ajlearnings.workbuddy.store.IUserStore;
import com.ajlearnings.workbuddy.translator.UserTranslator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
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
        if (this.existsByUserName((createUserRequest.getUserName()))) {
            throw new UserAlreadyExistException("Username already exist");
        }
        var newUser = UserTranslator.ToEntity(createUserRequest);
        newUser.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        try {
            var createdUser = userStore.add(newUser);
            return UserTranslator.ToResponse(createdUser);
        } catch(DuplicateKeyException ex) {
            throw new UserAlreadyExistException("Username already exist");
        }
    }

    @Override
    @Cacheable(key = "#userName")
    public User getUserByUserName(String userName) {
        var optionalUser = userStore.getByUserName(userName);
        return optionalUser.orElse(null);
    }

    private boolean existsByUserName(String userName) {
        return userStore.existsByUserName(userName);
    }
}
