package com.gustavosass.finance.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTransactionDTO {

	@NotNull
	private BigDecimal value;
	@NotNull
	private int installmentNumber;
	@NotNull
	private AccountDTO account;
}
