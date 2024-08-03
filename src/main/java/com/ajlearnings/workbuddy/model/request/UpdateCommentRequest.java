package com.ajlearnings.workbuddy.model.request;

import com.ajlearnings.workbuddy.annotation.ValidObjectId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCommentRequest {
    @NotBlank(message = "Comment cannot be empty")
    private String text;
}