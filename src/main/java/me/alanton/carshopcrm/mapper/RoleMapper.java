package me.alanton.carshopcrm.mapper;

import me.alanton.carshopcrm.dto.response.RoleResponse;
import me.alanton.carshopcrm.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
    RoleResponse roleToRoleResponse(Role role);
    List<RoleResponse> roleListToRoleResponseList(List<Role> roles);
}
