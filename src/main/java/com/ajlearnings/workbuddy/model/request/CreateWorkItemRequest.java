package com.ajlearnings.workbuddy.model.request;

import com.ajlearnings.workbuddy.annotation.ValidEnum;
import com.ajlearnings.workbuddy.enums.WorkItemType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateWorkItemRequest {
    @NotBlank(message = "Title cannot be blank")
    @NotNull(message = "Title cannot be null")
    private String title;
    private String description;
    @NotNull(message = "Type cannot be null")
    @ValidEnum(enumClass = WorkItemType.class)
    private String type;
}
