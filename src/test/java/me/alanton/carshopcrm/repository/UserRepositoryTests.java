package me.alanton.carshopcrm.repository;

import me.alanton.carshopcrm.config.JpaConfig;
import me.alanton.carshopcrm.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaConfig.class)
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Test get by id functionality")
    public void givenSavedUser_whenFindById_thenReturnUser() {
        // given
        User user1 = User.builder()
                .email("user1@gmail.com")
                .firstname("firstname1")
                .lastname("lastname1")
                .password("password1")
                .build();

        userRepository.save(user1);

        // when
        User obtainedUser = userRepository.findById(user1.getId())
                .orElse(null);

        // then
        assertThat(obtainedUser).isNotNull();
        assertThat(obtainedUser.getId()).isEqualTo(user1.getId());
    }

    @Test
    @DisplayName("Test get by email functionality")
    public void givenSavedUser_whenFindByEmail_thenReturnUser() {
        // given
        User user1 = User.builder()
                .email("user1@gmail.com")
                .firstname("firstname1")
                .lastname("lastname1")
                .password("password1")
                .build();

        userRepository.save(user1);

        // when
        User obtainedUser = userRepository.findByEmail(user1.getEmail())
                .orElse(null);

        // then
        assertThat(obtainedUser).isNotNull();
    }

    @Test
    @DisplayName("Test not existing get by email functionality")
    public void givenNotExistingUser_whenFindByEmail_thenReturnUser() {
        // given
        User user1 = User.builder()
                .email("user1@gmail.com")
                .firstname("firstname1")
                .lastname("lastname1")
                .password("password1")
                .build();

        // when
        User obtainedUser = userRepository.findByEmail(user1.getEmail())
                .orElse(null);

        // then
        assertThat(obtainedUser).isNull();
    }

    @Test
    @DisplayName("Test get all users with pagination")
    public void givenThreeSavedUsers_whenFindAll_thenReturnPageWithUsers() {
        // given
        User user1 = User.builder()
                .email("user1@gmail.com")
                .firstname("firstname1")
                .lastname("lastname1")
                .password("password1")
                .build();

        User user2 = User.builder()
                .email("user2@gmail.com")
                .firstname("firstname2")
                .lastname("lastname2")
                .password("password2")
                .build();

        User user3 = User.builder()
                .email("user3@gmail.com")
                .firstname("firstname3")
                .lastname("lastname3")
                .password("password3")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<User> users = userRepository.findAll(pageRequest);

        // then
        assertThat(users).isNotEmpty();
        assertThat(users.getTotalElements()).isEqualTo(3);
    }

    @Test
    @DisplayName("Test create user functionality")
    public void givenUser_whenSave_thenUserSaved() {
        // given
        User user1 = User.builder()
                .email("user1@gmail.com")
                .firstname("firstname1")
                .lastname("lastname1")
                .password("password1")
                .build();

        // when
        User savedUser = userRepository.save(user1);

        // then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(user1.getEmail());
    }

    @Test
    @DisplayName("Test delete user functionality")
    public void givenSavedUser_whenDeleteById_thenUserDeleted() {
        // given
        User user1 = User.builder()
                .email("user1@gmail.com")
                .firstname("firstname1")
                .lastname("lastname1")
                .password("password1")
                .build();

        userRepository.save(user1);

        // when
        userRepository.deleteById(user1.getId());

        // then
        User deletedUser = userRepository.findById(user1.getId())
                .orElse(null);

        assertThat(deletedUser).isNull();
    }

    @Test
    @DisplayName("Test user exist by email functionality")
    public void givenSavedUser_whenExistByEmail_thenReturnTrue() {
        // given
        User user1 = User.builder()
                .email("user1@gmail.com")
                .firstname("firstname1")
                .lastname("lastname1")
                .password("password1")
                .build();

        userRepository.save(user1);

        // when
        boolean exist = userRepository.existsByEmail(user1.getEmail());

        // then
        assertThat(exist).isTrue();
    }

    @Test
    @DisplayName("Test not existing user exist by email functionality")
    public void givenNotExistingUser_whenExistByEmail_thenReturnTrue() {
        // given
        User user1 = User.builder()
                .email("user1@gmail.com")
                .firstname("firstname1")
                .lastname("lastname1")
                .password("password1")
                .build();

        // when
        boolean exist = userRepository.existsByEmail(user1.getEmail());

        // then
        assertThat(exist).isFalse();
    }
}
