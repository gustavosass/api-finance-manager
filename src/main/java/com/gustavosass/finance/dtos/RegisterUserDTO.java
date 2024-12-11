package com.gustavosass.finance.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDTO {

    @NotNull
    private String fullName;
	@NotNull
    private String username;
	@NotNull
    private String password;
}
