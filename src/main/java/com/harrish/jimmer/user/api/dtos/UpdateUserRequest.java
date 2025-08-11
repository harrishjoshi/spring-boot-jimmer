package com.harrish.jimmer.user.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Username can only contain letters, numbers, dots, underscores, and hyphens")
        String username,

        @Email(message = "Email must be valid")
        @Size(max = 100, message = "Email must be less than 100 characters")
        String email,

        @Size(max = 50, message = "First name must be less than 50 characters")
        String firstName,

        @Size(max = 50, message = "Last name must be less than 50 characters")
        String lastName
) {
}