package me.alanton.carshopcrm.dto.response;

import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record UserResponse(
        UUID id,
        String email,
        String firstname,
        String lastname,
        Set<RoleResponse> roles
) {
}
