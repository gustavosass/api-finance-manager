package com.gustavosass.finance.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.gustavosass.finance.model.Role;

@DataJpaTest
@ActiveProfiles("test")
public class RoleRepositoryTest {

	@Autowired
	private RoleRepository roleRepository;

	private Role role;

	@BeforeEach
	void setUp() {
		this.role = new Role("Admin");
		roleRepository.save(role);
	}

	@Test
	@DisplayName("Found role in DB")
	void findByUsernameCase1() {
		Optional<Role> foundedRole = roleRepository.findByName(role.getName());
		Assertions.assertTrue(foundedRole.isPresent());
	}

	@Test
	@DisplayName("Not found role in DB")
	void findByUsernameCase2() {
		Optional<Role> foundedRole = roleRepository.findByName("name1");
		Assertions.assertFalse(foundedRole.isPresent());
	}

}
