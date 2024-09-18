package com.ajlearnings.workbuddy.repository;

import com.ajlearnings.workbuddy.entity.UserReaction;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IUserReactionRepository extends MongoRepository<UserReaction, ObjectId> {
    List<UserReaction> findAllByCommentIn(List<ObjectId> commentsId);
    void deleteAllByCommentIn(List<ObjectId> commentsId);
}
