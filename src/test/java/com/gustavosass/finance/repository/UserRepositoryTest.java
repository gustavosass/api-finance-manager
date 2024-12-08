package com.gustavosass.finance.repository;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.gustavosass.finance.enums.RoleEnum;
import com.gustavosass.finance.model.User;


@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    
    private User user;

    @BeforeEach
    void setUp() {
        this.user = new User(1L,"User", "user", "123", Set.of(RoleEnum.SUPER_ADMIN));
        userRepository.save(user);
    }

    @Test
    @DisplayName("Found user in DB")
    void findByUsernameCase1() {
        Optional<User> foundedUser = userRepository.findByUsername(user.getUsername());
        Assertions.assertTrue(foundedUser.isPresent());
    }
    
    @Test
    @DisplayName("Not found user in DB")
    void findByUsernameCase2() {
        user.setUsername("teste");
        Optional<User> foundedUser = userRepository.findByUsername(user.getUsername());
        Assertions.assertFalse(foundedUser.isPresent());
    }
    
    
    @Test
    void existsByUsername() {
    	Boolean userExists = userRepository.existsByUsername(user.getUsername());
    	Assertions.assertTrue(userExists);
    }


    
}
