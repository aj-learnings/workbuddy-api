package com.ajlearnings.workbuddy.store;

import com.ajlearnings.workbuddy.entity.User;
import com.ajlearnings.workbuddy.repository.IUserRepository;
import org.springframework.cache.annotation.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "user")
public class UserStore implements IUserStore {

    private final IUserRepository userRepository;

    public UserStore(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Caching(put = {
            @CachePut(key = "#user.username"),
            @CachePut(key = "#user.email")
    })
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    @Cacheable(key = "#username")
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                             .orElseThrow(() -> new UsernameNotFoundException("User not found with username : " + username));
    }

    @Override
    @Cacheable(key = "#email")
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                             .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Caching(put = {
            @CachePut(key = "#user.username"),
            @CachePut(key = "#user.email")
    })
    public User update(User user) {
        return userRepository.save(user);
    }
}
