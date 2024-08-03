package com.ajlearnings.workbuddy.entity;

import com.ajlearnings.workbuddy.enums.CommentType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "comment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {
    @NonNull
    private CommentType type;
    @NonNull
    private String text;
    @DBRef
    private WorkItem workItem;
}
