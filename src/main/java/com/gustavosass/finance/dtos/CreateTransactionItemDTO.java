package com.gustavosass.finance.dtos;

import java.util.Date;

import com.gustavosass.finance.enums.PaymentStatusEnum;
import com.gustavosass.finance.model.Transaction;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTransactionItemDTO {
	
    private Double value;
    private Transaction transaction;
    private Date dueDate;
    private int installmentNumber;
    private Date datePayment;
    private PaymentStatusEnum status;
}
