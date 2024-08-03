package com.ajlearnings.workbuddy.entity;

import com.ajlearnings.workbuddy.enums.WorkItemType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "workitem")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkItem extends BaseEntity {
    @NonNull
    private String title;
    private String description;
    @NonNull
    private WorkItemType type;
}
