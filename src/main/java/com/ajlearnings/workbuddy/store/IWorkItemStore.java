package com.ajlearnings.workbuddy.store;

import com.ajlearnings.workbuddy.entity.WorkItem;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IWorkItemStore {
    WorkItem add(WorkItem workItem);
    List<WorkItem> getAll();
    WorkItem get(ObjectId workItemId);
    WorkItem update(ObjectId workItemId, WorkItem workItem);
    void delete(ObjectId workItemId);
}
