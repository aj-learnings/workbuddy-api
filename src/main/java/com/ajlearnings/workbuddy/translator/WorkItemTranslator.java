package com.ajlearnings.workbuddy.translator;

import com.ajlearnings.workbuddy.entity.WorkItem;
import com.ajlearnings.workbuddy.enums.WorkItemType;
import com.ajlearnings.workbuddy.model.request.CreateWorkItemRequest;
import com.ajlearnings.workbuddy.model.response.WorkItemResponse;


public class WorkItemTranslator {
    public static WorkItem ToEntity(CreateWorkItemRequest createWorkItemRequest) {
        return WorkItem.builder()
                        .title(createWorkItemRequest.getTitle())
                        .description(createWorkItemRequest.getDescription())
                        .type(WorkItemType.valueOf(createWorkItemRequest.getType()))
                        .build();
    }

    public static WorkItemResponse ToResponse(WorkItem workItem) {
        return WorkItemResponse.builder()
                                .id(workItem.getId().toString())
                                .title(workItem.getTitle())
                                .type(workItem.getType().toString())
                                .description(workItem.getDescription())
                                .created(workItem.getCreatedAt())
                                .updated(workItem.getUpdatedAt())
                                .build();
    }
}
