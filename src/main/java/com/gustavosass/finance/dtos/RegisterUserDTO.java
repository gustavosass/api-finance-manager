package com.gustavosass.finance.dtos;

import com.gustavosass.finance.enums.RoleEnum;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RegisterUserDTO {
	
	@NotNull
    private String username;
	@NotNull
    private String password;
	@NotNull
    private String fullName;
	@NotNull
    private Set<RoleEnum> roles;
}
