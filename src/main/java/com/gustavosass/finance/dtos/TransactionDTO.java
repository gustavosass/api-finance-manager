package com.gustavosass.finance.dtos;

import java.util.Date;

import com.gustavosass.finance.enums.AccountingEntryTypeEnum;
import com.gustavosass.finance.enums.PaymentStatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO {
	
	private long id;
	private Double value;
	private int installmentNumbers; 
    private Date createdAt;
    private Date updatedAt;
    private AccountDTO account;
    private PaymentStatusEnum status;
    private AccountingEntryTypeEnum accountingEntryType;
    	
}
