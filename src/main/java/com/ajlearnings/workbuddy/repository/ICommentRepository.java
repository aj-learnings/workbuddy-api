package com.ajlearnings.workbuddy.repository;

import com.ajlearnings.workbuddy.entity.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends MongoRepository<Comment, ObjectId> {

    List<Comment> findAllByWorkItem(ObjectId workItemId);
    void deleteAllByWorkItem(ObjectId workItemId);
}
