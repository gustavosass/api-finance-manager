package com.gustavosass.finance.dtos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDTO {
	
	private String fullName;
	private String username;
	private String password;
	private Set<String> roles = new HashSet<>();
	
}
