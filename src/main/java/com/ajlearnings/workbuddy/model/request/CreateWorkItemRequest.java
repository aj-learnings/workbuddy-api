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
    private String title;
    private String description;
    @NotNull(message = "Work item cannot be blank")
    @ValidEnum(enumClass = WorkItemType.class)
    private String type;
}
