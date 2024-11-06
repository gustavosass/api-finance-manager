package com.gustavosass.finance.dtos;

import java.math.BigDecimal;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {
	
	private Long id;
	private String name;
	private BigDecimal balance;
	private Set<UserDTO> users;
}
