package com.ajlearnings.workbuddy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailData {
    private String to;
    private String subject;
    private String body;
}
