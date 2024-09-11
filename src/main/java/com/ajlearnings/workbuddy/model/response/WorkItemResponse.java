package com.ajlearnings.workbuddy.model.response;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkItemResponse {
    private String id;
    private String title;
    private String type;
    private String description;
    private Date created;
    private Date updated;
    private String createdBy;
}
