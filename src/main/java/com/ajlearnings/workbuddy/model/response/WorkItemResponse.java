package com.ajlearnings.workbuddy.model.response;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkItemResponse implements Serializable {
    private String id;
    private String title;
    private String description;
    private LocalDateTime created;
    private LocalDateTime updated;
    private List<CommentResponse> comments;
}
