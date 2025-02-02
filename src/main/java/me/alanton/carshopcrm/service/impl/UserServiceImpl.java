package me.alanton.carshopcrm.service.impl;

import lombok.RequiredArgsConstructor;
import me.alanton.carshopcrm.dto.request.UserRequest;
import me.alanton.carshopcrm.dto.response.UserResponse;
import me.alanton.carshopcrm.entity.Role;
import me.alanton.carshopcrm.entity.User;
import me.alanton.carshopcrm.exception.impl.BusinessException;
import me.alanton.carshopcrm.exception.impl.BusinessExceptionReason;
import me.alanton.carshopcrm.mapper.RoleMapper;
import me.alanton.carshopcrm.mapper.UserMapper;
import me.alanton.carshopcrm.repository.RoleRepository;
import me.alanton.carshopcrm.repository.UserRepository;
import me.alanton.carshopcrm.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public UserResponse getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionReason.USER_NOT_FOUND));

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(BusinessExceptionReason.USER_NOT_FOUND));

        return userMapper.toUserResponse(user);
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);

        return users.map(userMapper::toUserResponse);
    }

    @Override
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.email())) {
            throw new BusinessException(BusinessExceptionReason.USER_IS_ALREADY_EXIST);
        }

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new BusinessException(BusinessExceptionReason.ROLE_NOT_FOUND));

        Set<Role> roles = new HashSet<>();

        if (userRequest.roles() != null && !userRequest.roles().isEmpty()) {
             roles = userRequest.roles().stream()
                    .map(roleRequest ->
                            roleRepository.findByName(roleRequest.name())
                                    .orElseThrow(() -> new BusinessException(BusinessExceptionReason.ROLE_NOT_FOUND)))
                    .collect(Collectors.toSet());
        }

        roles.add(userRole);

        User user = User.builder()
                .email(userRequest.email())
                .firstname(userRequest.firstname())
                .lastname(userRequest.lastname())
                .password(userRequest.password())
                .roles(roles)
                .build();

        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateUser(UUID id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionReason.USER_NOT_FOUND));

        user.setEmail(userRequest.email());
        user.setFirstname(userRequest.firstname());
        user.setLastname(userRequest.lastname());
        user.setPassword(userRequest.password());

        return userMapper.toUserResponse(user);
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new BusinessException(BusinessExceptionReason.USER_NOT_FOUND);
        }

        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void enableUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionReason.USER_NOT_FOUND));

        user.setEnabled(true);

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void disableUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionReason.USER_NOT_FOUND));

        user.setEnabled(false);

        userRepository.save(user);
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
