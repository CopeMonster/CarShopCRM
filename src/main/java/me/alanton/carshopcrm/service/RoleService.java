package me.alanton.carshopcrm.service;

import me.alanton.carshopcrm.dto.request.RoleRequest;
import me.alanton.carshopcrm.dto.response.RoleResponse;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    RoleResponse getRoleById(UUID id);
    RoleResponse getRoleByName(String name);
    List<RoleResponse> getAllRoles();
    RoleResponse createRole(RoleRequest roleRequest);
    RoleResponse updateRole(UUID id, RoleRequest roleRequest);
    void deleteRole(UUID id);
    void enableRole(UUID id);
    void disableRole(UUID id);
    boolean isRoleExistByName(String name);
}
