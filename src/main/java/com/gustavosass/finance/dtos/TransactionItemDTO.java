package com.gustavosass.finance.dtos;

import com.gustavosass.finance.enums.PaymentStatusEnum;
import com.gustavosass.finance.model.Transaction;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

public class TransactionItemDTO {

    private long id;
    private Double value;
    private Transaction transaction;
    private int installmentNumber;
    private PaymentStatusEnum status;
    private Date datePayment;
    private Date createdAt;
    private Date updatedAt;
}
