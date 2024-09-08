package com.ajlearnings.workbuddy.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequest {
    @NotBlank(message = "Comment cannot be empty")
    private String text;
}
