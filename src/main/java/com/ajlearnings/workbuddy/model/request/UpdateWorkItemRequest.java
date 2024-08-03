package com.ajlearnings.workbuddy.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateWorkItemRequest {
    @NotBlank(message = "Title cannot be blank")
    private String title;
    private String description;
}
