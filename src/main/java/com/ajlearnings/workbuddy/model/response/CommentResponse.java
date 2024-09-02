package com.ajlearnings.workbuddy.model.response;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse implements Serializable {
    private String id;
    private String type;
    private String text;
    private Date created;
    private Date updated;
}
