package com.ajlearnings.workbuddy.model.response;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String guid;
    private String message;
    private Integer statusCode;
    private String statusName;
    private String path;
    private String method;
    private LocalDateTime timestamp;
}
