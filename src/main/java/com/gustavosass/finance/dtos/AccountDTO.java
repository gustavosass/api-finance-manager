package com.gustavosass.finance.dtos;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {
	
	private Long id;
	private String name;
	private Set<UserDTO> users;
}
