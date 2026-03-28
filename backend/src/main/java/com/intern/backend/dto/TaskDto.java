package com.intern.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskDto {
    private Long id;

    @NotBlank
    private String title;

    private String description;
    
    // Default PENDING if null
    private String status;
}
