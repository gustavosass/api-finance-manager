package com.gustavosass.finance.mapper;

import com.gustavosass.finance.dtos.TransactionItemDTO;
import com.gustavosass.finance.model.TransactionItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionItemMapper {

    @Autowired
    private TransactionMapper transactionMapper;
    
    
    public TransactionItemDTO toDto(TransactionItem transactionItem) {
    	TransactionItemDTO entity = new TransactionItemDTO();
    	entity.setId(transactionItem.getId());
    	entity.setValue(transactionItem.getValue());
    	entity.setDueDate(transactionItem.getDueDate());
    	entity.setInstallmentNumber(transactionItem.getInstallmentNumber());
    	entity.setStatus(transactionItem.getStatus());
    	entity.setDatePayment(transactionItem.getDatePayment());
    	entity.setCreatedAt(transactionItem.getCreatedAt());
    	entity.setUpdatedAt(transactionItem.getUpdatedAt());
    	entity.setTransactionDto(transactionMapper.toDto(transactionItem.getTransaction()));
    	
        return entity;
    }

    public TransactionItem toEntity(TransactionItemDTO transactionItemDTO) {
    	TransactionItem entity = new TransactionItem();
    	entity.setId(transactionItemDTO.getId());
    	entity.setValue(transactionItemDTO.getValue());
    	entity.setDueDate(transactionItemDTO.getDueDate());
    	entity.setInstallmentNumber(transactionItemDTO.getInstallmentNumber());
    	entity.setStatus(transactionItemDTO.getStatus());
    	entity.setDatePayment(transactionItemDTO.getDatePayment());
    	entity.setCreatedAt(transactionItemDTO.getCreatedAt());
    	entity.setUpdatedAt(transactionItemDTO.getUpdatedAt());
    	entity.setTransaction(transactionMapper.toEntity(transactionItemDTO.getTransactionDto()));
    	
        return entity;
        
    }

}
