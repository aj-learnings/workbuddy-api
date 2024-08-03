package com.ajlearnings.workbuddy.model.response;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse implements Serializable {
    private String id;
    private String type;
    private String text;
    private LocalDateTime created;
    private LocalDateTime updated;
}
