package me.alanton.carshopcrm.repository;

import me.alanton.carshopcrm.config.JpaConfig;
import me.alanton.carshopcrm.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaConfig.class)
public class RoleRepositoryTests {
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void init() {
        roleRepository.deleteAll();
    }

    @Test
    @DisplayName("Test role find by id functionality")
    public void givenSavedRole_whenFindById_thenReturnRole() {
        // given
        Role role1 = Role.builder()
                .name("ADMIN")
                .build();

        roleRepository.save(role1);

        // when
        Role obtainedRole = roleRepository.findById(role1.getId())
                .orElse(null);

        // then
        assertThat(obtainedRole).isNotNull();
        assertThat(obtainedRole.getId()).isEqualTo(role1.getId());
    }

    @Test
    @DisplayName("Test tole find by name functionality")
    public void givenSavedRole_whenFindByEmail_thenReturnRole() {
        // given
        Role role1 = Role.builder()
                .name("ADMIN")
                .build();

        roleRepository.save(role1);

        // when
        Role obtainedRole = roleRepository.findByName(role1.getName())
                .orElse(null);

        // then
        assertThat(obtainedRole).isNotNull();
        assertThat(obtainedRole.getId()).isEqualTo(role1.getId());
        assertThat(obtainedRole.getName()).isEqualTo(role1.getName());
    }

    @Test
    @DisplayName("Test not existing role find by name functionality")
    public void givenSavedRole_whenFindByName_thenReturnRole() {
        // given
        Role role1 = Role.builder()
                .name("ADMIN")
                .build();

        // when
        Role obtainedRole = roleRepository.findByName(role1.getName())
                .orElse(null);

        // then
        assertThat(obtainedRole).isNull();
    }

    @Test
    @DisplayName("Test create role functionality")
    public void givenRole_whenSave_thenRoleSaved() {
        // given
        Role role1 = Role.builder()
                .name("ADMIN")
                .build();

        // when
        Role savedRole = roleRepository.save(role1);

        // then
        assertThat(savedRole).isNotNull();
        assertThat(savedRole.getName()).isEqualTo(role1.getName());
    }

    @Test
    @DisplayName("Test delete role functionality")
    public void givenRole_whenDeleteById_thenUpdateRole() {
        // given
        Role role1 = Role.builder()
                .name("ADMIN")
                .build();

        roleRepository.save(role1);

        // when
        roleRepository.deleteById(role1.getId());

        // then
        Role deletedRole = roleRepository.findById(role1.getId())
                .orElse(null);

        assertThat(deletedRole).isNull();
    }

    @Test
    @DisplayName("Test role existing by name functionality")
    public void givenSavedRole_whenExistsByName_thenReturnTrue() {
        // given
        Role role1 = Role.builder()
                .name("ADMIN")
                .build();

        roleRepository.save(role1);

        // when
        boolean exist = roleRepository.existsByName(role1.getName());

        // then
        assertThat(exist).isTrue();
    }

    @Test
    @DisplayName("Test not existing role existing by name functionality")
    public void givenNotExistingRole_whenExistsByName_thenReturnTrue() {
        // given
        Role role1 = Role.builder()
                .name("ADMIN")
                .build();

        // when
        boolean exist = roleRepository.existsByName(role1.getName());

        // then
        assertThat(exist).isFalse();
    }
}
