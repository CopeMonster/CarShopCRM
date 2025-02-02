package me.alanton.carshopcrm.service;

import me.alanton.carshopcrm.dto.request.UserRequest;
import me.alanton.carshopcrm.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {
    UserResponse getUserById(UUID id);
    UserResponse getUserByEmail(String email);
    Page<UserResponse> getAllUsers(Pageable pageable);
    UserResponse createUser(UserRequest userRequest);
    UserResponse updateUser(UUID id, UserRequest userRequest);
    void deleteUser(UUID id);
    void enableUser(UUID id);
    void disableUser(UUID id);
    boolean isUserExistByEmail(String email);
}
