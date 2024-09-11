package com.ajlearnings.workbuddy.entity;

import com.ajlearnings.workbuddy.enums.WorkItemType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "workitem")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkItem extends BaseEntity implements Serializable {
    @NonNull
    private String title;
    private String description;
    @NonNull
    private WorkItemType type;
    @DBRef
    private User user;
}
