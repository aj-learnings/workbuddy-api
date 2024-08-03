package com.ajlearnings.workbuddy.repository;

import com.ajlearnings.workbuddy.entity.WorkItem;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWorkItemRepository extends MongoRepository<WorkItem, ObjectId> {
}
