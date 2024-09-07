package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.model.request.CreateWorkItemRequest;
import com.ajlearnings.workbuddy.model.request.UpdateWorkItemRequest;
import com.ajlearnings.workbuddy.model.response.WorkItemResponse;
import com.ajlearnings.workbuddy.store.IWorkItemStore;
import com.ajlearnings.workbuddy.translator.WorkItemTranslator;
import org.bson.types.ObjectId;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "workitem")
public class WorkItemService implements IWorkItemService {

    private final IWorkItemStore workItemStore;
    private final ICommentService commentService;

    public WorkItemService(IWorkItemStore workItemStore, ICommentService commentService) {
        this.workItemStore = workItemStore;
        this.commentService = commentService;
    }

    @Override
    @CacheEvict(key = "'all'")
    public WorkItemResponse addWorkItem(CreateWorkItemRequest createWorkItemRequest) {
        var addedWorkItem = workItemStore.add(WorkItemTranslator.ToEntity(createWorkItemRequest));
        return WorkItemTranslator.ToResponse(addedWorkItem);
    }

    @Override
    @Cacheable(key = "'all'")
    public List<WorkItemResponse> getAllWorkItem() {
        var workItems = workItemStore.getAll();
        workItems.sort((workItem1, workItem2) -> workItem2.getUpdatedAt().compareTo(workItem1.getUpdatedAt()));
        return workItems.stream().map(WorkItemTranslator::ToResponse).toList();
    }

    @Override
    @Cacheable(key = "#workItemId")
    public WorkItemResponse getSingleWorkItem(ObjectId workItemId) {
        var workItem = workItemStore.get(workItemId);
        return WorkItemTranslator.ToResponse(workItem);
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
        return WorkItemTranslator.ToResponse(updatedWorkItem);
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
