package me.alanton.carshopcrm.mapper;

import me.alanton.carshopcrm.dto.response.UserResponse;
import me.alanton.carshopcrm.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {RoleMapper.class}
)
public interface UserMapper {
    UserResponse toUserResponse(User user);
}
