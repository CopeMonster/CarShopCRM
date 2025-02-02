package me.alanton.carshopcrm.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record UserRequest(
        String email,
        String firstname,
        String lastname,
        String password,
        List<RoleRequest> roles
) {
}
