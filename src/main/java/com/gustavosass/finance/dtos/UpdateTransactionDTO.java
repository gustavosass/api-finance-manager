package com.gustavosass.finance.dtos;

import com.gustavosass.finance.enums.AccountingEntryTypeEnum;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UpdateTransactionDTO {
	@NotNull
	private long id;
	@NotNull
	@Min(1)
	private Double value;
	@NotNull
	@Min(1)
	private int installmentNumbers;
	@NotNull
    private AccountDTO account;
	@NotNull
    private AccountingEntryTypeEnum accountingEntryType;

}
