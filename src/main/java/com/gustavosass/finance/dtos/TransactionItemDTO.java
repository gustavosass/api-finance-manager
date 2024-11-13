package com.gustavosass.finance.dtos;

import com.gustavosass.finance.enums.PaymentStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TransactionItemDTO {

    private long id;
    private Double value;
    private TransactionDTO transactionDto;
    private Date dueDate;
    private int installmentNumber;
    private PaymentStatusEnum status;
    private Date datePayment;
    private Date createdAt;
    private Date updatedAt;
}
