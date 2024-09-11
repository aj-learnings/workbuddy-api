package com.ajlearnings.workbuddy.store;

import com.ajlearnings.workbuddy.entity.User;
import com.ajlearnings.workbuddy.repository.IUserRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@CacheConfig(cacheNames = "user")
public class UserStore implements IUserStore {

    private final IUserRepository userRepository;

    public UserStore(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    @Cacheable(key = "#userName")
    public Optional<User> getByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return userRepository.existsByUserName(userName);
    }
}
