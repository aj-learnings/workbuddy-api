package com.ajlearnings.workbuddy.model.response;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkItemResponse implements Serializable {
    private String id;
    private String title;
    private String type;
    private String description;
    private Date created;
    private Date updated;
}
