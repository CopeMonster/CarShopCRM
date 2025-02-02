package me.alanton.carshopcrm.mapper;

import me.alanton.carshopcrm.dto.response.RoleResponse;
import me.alanton.carshopcrm.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
    RoleResponse toRoleResponse(Role role);
}
