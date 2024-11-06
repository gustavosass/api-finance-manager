package com.gustavosass.finance.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDTO {
	@NotNull
    private String username;
	@NotNull
    private String password;
}
