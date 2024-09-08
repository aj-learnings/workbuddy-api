package com.ajlearnings.workbuddy.repository;

import com.ajlearnings.workbuddy.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByUserName(String userName);
}
