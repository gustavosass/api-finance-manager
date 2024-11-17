package com.gustavosass.finance.repository;

import com.gustavosass.finance.enums.RoleEnum;
import com.gustavosass.finance.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.Set;


@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Found user in DB")
    void findByUsernameCase1() {
        User userCreated = createUser();
        Optional<User> foundedUser = userRepository.findByUsername(userCreated.getUsername());
        Assertions.assertTrue(foundedUser.isPresent());
    }

    @Test
    @DisplayName("Not found user in DB")
    void findByUsernameCase2() {
        String username = "teste";
        Optional<User> foundedUser = userRepository.findByUsername(username);
        Assertions.assertFalse(foundedUser.isPresent());
    }

    private User createUser() {
        User user = new User();
        user.setFullName("Gustavo Sass");
        user.setUsername("gustavo");
        user.setPassword("gustavo");
        user.setRoles(Set.of(RoleEnum.SUPER_ADMIN));
        userRepository.save(user);
        return user;
    }

}
