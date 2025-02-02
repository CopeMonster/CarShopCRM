package me.alanton.carshopcrm.dto.request;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record UserRequest(
        UUID id,
        String email,
        String firstname,
        String lastname,
        String password,
        List<RoleRequest> roles
) {
}
