package com.ajlearnings.workbuddy.controller;

import com.ajlearnings.workbuddy.annotation.ValidObjectId;
import com.ajlearnings.workbuddy.model.request.CreateWorkItemRequest;
import com.ajlearnings.workbuddy.model.request.UpdateWorkItemRequest;
import com.ajlearnings.workbuddy.model.response.WorkItemResponse;
import com.ajlearnings.workbuddy.service.IWorkItemService;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workitem")
@Validated
public class WorkItemController {

    @Autowired
    private IWorkItemService workItemService;

    @PostMapping
    public ResponseEntity<WorkItemResponse> addWorkItem(@Valid @RequestBody CreateWorkItemRequest createWorkItemRequest) {
        var addedWorkItem = workItemService.addWorkItem(createWorkItemRequest);
        return new ResponseEntity<>(addedWorkItem, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WorkItemResponse>> getAllWorkItems() {
        var workItems = workItemService.getAllWorkItem();
        return new ResponseEntity<>(workItems, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<WorkItemResponse> getSingleWorkItem(@PathVariable("id") @ValidObjectId String workItemId) {
        var workItem = workItemService.getSingleWorkItem(new ObjectId(workItemId));
        return new ResponseEntity<>(workItem, HttpStatus.OK);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<WorkItemResponse> updateWorkItem(@PathVariable("id") @ValidObjectId String workItemId, @Valid @RequestBody UpdateWorkItemRequest updateWorkItemRequest) {
        var updatedWorkItem = workItemService.updateWorkItem(new ObjectId(workItemId), updateWorkItemRequest);
        return new ResponseEntity<>(updatedWorkItem, HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteWorkItem(@PathVariable("id") @ValidObjectId String workItemId) {
        boolean isDeleted = workItemService.deleteWorkItem(new ObjectId(workItemId));
        return new ResponseEntity<>(isDeleted, HttpStatus.OK);
    }
}
