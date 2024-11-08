package com.gustavosass.finance.dtos;

import java.math.BigDecimal;
import java.util.Date;


import com.gustavosass.finance.enums.StatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO {
	
	private long id;
	private BigDecimal value;
	private int installmentNumber; 
    private Date createdAt;
    private Date updatedAt;
    private AccountDTO account;
    private StatusEnum status;
    	
}
