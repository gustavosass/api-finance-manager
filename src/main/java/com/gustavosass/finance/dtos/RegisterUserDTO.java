package com.gustavosass.finance.dtos;

import com.gustavosass.finance.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RegisterUserDTO {
    private String username;
    private String password;
    private String fullName;
    private Set<RoleEnum> roles;
}
