package com.gustavosass.finance.dtos;

import com.gustavosass.finance.enums.PaymentStatusEnum;
import com.gustavosass.finance.model.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TransactionItemDTO {

    private long id;
    private Double value;
    private Transaction transaction;
    private Date dueDate;
    private int installmentNumber;
    private PaymentStatusEnum status;
    private Date datePayment;
    private Date createdAt;
    private Date updatedAt;
}
