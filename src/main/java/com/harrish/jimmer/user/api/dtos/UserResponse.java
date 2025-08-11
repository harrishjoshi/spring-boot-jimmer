package com.harrish.jimmer.user.api.dtos;

import java.time.LocalDateTime;

public record UserResponse(
        long id,
        String username,
        String email,
        String firstName,
        String lastName,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}