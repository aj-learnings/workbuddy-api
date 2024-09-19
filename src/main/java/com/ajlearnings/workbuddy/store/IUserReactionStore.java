package com.ajlearnings.workbuddy.store;

import com.ajlearnings.workbuddy.entity.UserReaction;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserReactionStore {
    UserReaction add(UserReaction userReaction);
    UserReaction get(ObjectId userReactionId);
    List<UserReaction> getAllForComments(List<ObjectId> commentsId);
    UserReaction update(UserReaction userReaction);
    void delete(ObjectId userReactionId);
    void deleteAllForComments(List<ObjectId> commentsId);
}
