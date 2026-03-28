package com.intern.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
    
    // Optional role selection during signup, defaults to ROLE_USER otherwise
    private String role;
}
