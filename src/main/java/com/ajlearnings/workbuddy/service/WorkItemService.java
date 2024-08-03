package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.model.request.CreateWorkItemRequest;
import com.ajlearnings.workbuddy.model.request.UpdateWorkItemRequest;
import com.ajlearnings.workbuddy.model.response.WorkItemResponse;
import com.ajlearnings.workbuddy.store.ICommentStore;
import com.ajlearnings.workbuddy.store.IWorkItemStore;
import com.ajlearnings.workbuddy.translator.CommentTranslator;
import com.ajlearnings.workbuddy.translator.WorkItemTranslator;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "workitem")
public class WorkItemService implements IWorkItemService {

    @Autowired
    private IWorkItemStore workItemStore;

    @Autowired
    private ICommentService commentService;

    @Override
    @CacheEvict(key = "'all'")
    public WorkItemResponse addWorkItem(CreateWorkItemRequest createWorkItemRequest) {
        var addedWorkItem = workItemStore.add(WorkItemTranslator.ToEntity(createWorkItemRequest));
        return WorkItemTranslator.ToResponse(addedWorkItem, null);
    }

    @Override
    @Cacheable(key = "'all'")
    public List<WorkItemResponse> getAllWorkItem() {
        var workItems = workItemStore.getAll();
        return workItems.stream().map(workItem -> WorkItemTranslator.ToResponse(workItem, null)).toList();
    }

    @Override
    @Cacheable(key = "#workItemId")
    public WorkItemResponse getSingleWorkItem(ObjectId workItemId) {
        var workItem = workItemStore.get(workItemId);
        var comments = commentService.getAllCommentsPerWorkItem(workItemId);
        return WorkItemTranslator.ToResponse(workItem, comments);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(key = "'all'"),
            @CacheEvict(key = "#workItemId")
    })
    public WorkItemResponse updateWorkItem(ObjectId workItemId, UpdateWorkItemRequest updateWorkItem) {
        var workItem = workItemStore.get(workItemId);
        workItem.setTitle(updateWorkItem.getTitle());
        workItem.setDescription((updateWorkItem.getDescription()));
        var updatedWorkItem = workItemStore.update(workItem);
        return WorkItemTranslator.ToResponse(updatedWorkItem, null);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(key = "'all'"),
            @CacheEvict(key = "#workItemId")
    })
    public boolean deleteWorkItem(ObjectId workItemId) {
        var workItem = workItemStore.get(workItemId);
        workItemStore.delete(workItem.getId());
        commentService.deleteAllCommentsPerWorkItem(workItem.getId());
        return true;
    }
}
