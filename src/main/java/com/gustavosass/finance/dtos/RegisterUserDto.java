package com.gustavosass.finance.dtos;

import com.gustavosass.finance.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
    private String username;
    private String password;
    private String fullName;
    private Role role;
}
