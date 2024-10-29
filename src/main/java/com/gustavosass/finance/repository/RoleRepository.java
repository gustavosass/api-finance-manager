package com.gustavosass.finance.repository;

import com.gustavosass.finance.enums.RoleEnum;
import com.gustavosass.finance.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}
