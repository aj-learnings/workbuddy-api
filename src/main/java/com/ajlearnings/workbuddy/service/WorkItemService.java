package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.model.request.CreateWorkItemRequest;
import com.ajlearnings.workbuddy.model.request.UpdateWorkItemRequest;
import com.ajlearnings.workbuddy.model.response.WorkItemResponse;
import com.ajlearnings.workbuddy.store.IUserStore;
import com.ajlearnings.workbuddy.store.IWorkItemStore;
import com.ajlearnings.workbuddy.translator.WorkItemTranslator;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@CacheConfig(cacheNames = "workitem")
public class WorkItemService implements IWorkItemService {

    private final IWorkItemStore workItemStore;
    private final ICommentService commentService;
    private final IUserStore userStore;

    public WorkItemService(IWorkItemStore workItemStore, ICommentService commentService, IUserStore userStore) {
        this.workItemStore = workItemStore;
        this.commentService = commentService;
        this.userStore = userStore;
    }

    @Override
    @CacheEvict(key = "'all'")
    public WorkItemResponse addWorkItem(CreateWorkItemRequest createWorkItemRequest) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userName = authentication.getName();
        var user = userStore.getByUserName(userName);
        var workItem = WorkItemTranslator.ToEntity(createWorkItemRequest);
        workItem.setUser(user);
        var addedWorkItem = workItemStore.add(workItem);
        return WorkItemTranslator.ToResponse(addedWorkItem);
    }

    @Override
    public List<WorkItemResponse> getAllWorkItem() {
        var workItems = workItemStore.getAll();
        workItems.sort((workItem1, workItem2) -> workItem2.getUpdatedAt().compareTo(workItem1.getUpdatedAt()));
        return workItems.stream().map(WorkItemTranslator::ToResponse).toList();
    }

    @Override
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
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userName = authentication.getName();
        var user = userStore.getByUserName(userName);
        var workItem = workItemStore.get(workItemId);
        if (!workItem.getUser().getUsername().equals(user.getUsername())) {
            throw new AccessDeniedException("You do not have permission to update this work item");
        }
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
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userName = authentication.getName();
        var user = userStore.getByUserName(userName);
        var workItem = workItemStore.get(workItemId);
        if (!workItem.getUser().getUsername().equals(user.getUsername())) {
            throw new AccessDeniedException("You do not have permission to delete this work item");
        }
        workItemStore.delete(workItem.getId());
        commentService.deleteAllCommentsPerWorkItem(workItem.getId());
        return true;
    }
}
