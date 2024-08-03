package com.ajlearnings.workbuddy.service;

import com.ajlearnings.workbuddy.model.request.CreateWorkItemRequest;
import com.ajlearnings.workbuddy.model.request.UpdateWorkItemRequest;
import com.ajlearnings.workbuddy.model.response.WorkItemResponse;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IWorkItemService {

    WorkItemResponse addWorkItem(CreateWorkItemRequest createWorkItemRequest);
    List<WorkItemResponse> getAllWorkItem();
    WorkItemResponse getSingleWorkItem(ObjectId workItemId);
    WorkItemResponse updateWorkItem(ObjectId workItemId, UpdateWorkItemRequest updateWorkItem);
    boolean deleteWorkItem(ObjectId workItemId);
}
