package me.alanton.carshopcrm.bootstrap;

import lombok.RequiredArgsConstructor;
import me.alanton.carshopcrm.dto.request.RoleRequest;
import me.alanton.carshopcrm.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RoleInitializer implements CommandLineRunner {
    private final RoleService roleService;
    private final String[] DEFAULT_ROLES = {
            "USER",
            "SYS_ADMIN",
            "ADMIN",
            "OPERATOR",
            "MANAGER",
            "HEAD_OF_SALES",
            "MARKETER",
            "HEAD_OF_SERVICE",
            "SERVICE_MANAGER"
    };

    @Override
    public void run(String... args) throws Exception {
        for (String role : DEFAULT_ROLES) {
            if (!roleService.isRoleExistByName(role)) {
                RoleRequest roleRequest = new RoleRequest(role);

                roleService.createRole(roleRequest);
            }
        }
    }
}
