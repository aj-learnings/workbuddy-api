package com.ajlearnings.workbuddy.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserReactionRequest {
    @NotNull(message = "reaction cannot be null")
    private Boolean isLiked;
}
