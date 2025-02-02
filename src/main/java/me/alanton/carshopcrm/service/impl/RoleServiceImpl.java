package me.alanton.carshopcrm.service.impl;

import lombok.RequiredArgsConstructor;
import me.alanton.carshopcrm.dto.request.RoleRequest;
import me.alanton.carshopcrm.dto.response.RoleResponse;
import me.alanton.carshopcrm.entity.Role;
import me.alanton.carshopcrm.exception.impl.BusinessException;
import me.alanton.carshopcrm.exception.impl.BusinessExceptionReason;
import me.alanton.carshopcrm.mapper.RoleMapper;
import me.alanton.carshopcrm.repository.RoleRepository;
import me.alanton.carshopcrm.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse getRoleById(UUID id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionReason.ROLE_NOT_FOUND));

        return roleMapper.toRoleResponse(role);
    }

    @Override
    public RoleResponse getRoleByName(String name) {
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new BusinessException(BusinessExceptionReason.ROLE_NOT_FOUND));

        return roleMapper.toRoleResponse(role);
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        List<Role> roles = roleRepository.findAll();

        return roles.stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    @Override
    @Transactional
    public RoleResponse createRole(RoleRequest roleRequest) {
        if (roleRepository.existsByName(roleRequest.name())) {
            throw new BusinessException(BusinessExceptionReason.ROLE_IS_ALREADY_EXIST);
        }

        Role role = Role.builder()
                .name(roleRequest.name())
                .build();

        roleRepository.save(role);

        return roleMapper.toRoleResponse(role);
    }

    @Override
    @Transactional
    public RoleResponse updateRole(UUID id, RoleRequest roleRequest) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionReason.ROLE_NOT_FOUND));

        role.setName(roleRequest.name());

        roleRepository.save(role);

        return roleMapper.toRoleResponse(role);
    }

    @Override
    @Transactional
    public void deleteRole(UUID id) {
        if (!roleRepository.existsById(id)) {
            throw new BusinessException(BusinessExceptionReason.ROLE_NOT_FOUND);
        }

        roleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void enableRole(UUID id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionReason.ROLE_NOT_FOUND));

        role.setEnabled(true);

        roleRepository.save(role);
    }

    @Override
    @Transactional
    public void disableRole(UUID id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(BusinessExceptionReason.ROLE_NOT_FOUND));

        role.setEnabled(false);

        roleRepository.save(role);
    }

    @Override
    public boolean isRoleExistByName(String name) {
        return roleRepository.existsByName(name);
    }
}
