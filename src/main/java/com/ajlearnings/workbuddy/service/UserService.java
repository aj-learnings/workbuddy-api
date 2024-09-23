package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.entity.User;
import com.ajlearnings.workbuddy.exception.UserAlreadyExistException;
import com.ajlearnings.workbuddy.model.request.CreateUserRequest;
import com.ajlearnings.workbuddy.model.response.UserResponse;
import com.ajlearnings.workbuddy.store.IUserStore;
import com.ajlearnings.workbuddy.translator.UserTranslator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
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
        if (this.existsByEmail((createUserRequest.getEmail()))) {
            throw new UserAlreadyExistException("Email already exist");
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
    public User getUserByUserNameOrEmail(String userNameOrEmail) {
        try {
            return userStore.getByUserName(userNameOrEmail);
        } catch (Exception ex) {
            return userStore.getByEmail(userNameOrEmail);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        return userStore.existsByEmail(email);
    }

    private boolean existsByUserName(String userName) {
        return userStore.existsByUserName(userName);
    }
}
