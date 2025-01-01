package com.gustavosass.finance.mapper;

import com.gustavosass.finance.dtos.CreateTransactionItemDTO;
import com.gustavosass.finance.dtos.TransactionItemDTO;
import com.gustavosass.finance.model.TransactionItem;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionItemMapper {
    
    @Autowired
    private ModelMapper modelMapper;
    
    public TransactionItemDTO toDto(TransactionItem transactionItem) {    	
    	modelMapper.typeMap(TransactionItem.class, TransactionItemDTO.class).addMapping(TransactionItem::getTransaction, TransactionItemDTO::setTransactionDto);
    	return modelMapper.map(transactionItem, TransactionItemDTO.class);
    	
    }

    public TransactionItem toEntity(TransactionItemDTO transactionItemDTO) {
    	modelMapper.typeMap(TransactionItemDTO.class, TransactionItem.class).addMapping(TransactionItemDTO::getTransactionDto, TransactionItem::setTransaction);
    	return modelMapper.map(transactionItemDTO, TransactionItem.class);        
    }
    
    public TransactionItem toEntity(CreateTransactionItemDTO createTransactionItemDto) {
    	modelMapper.typeMap(CreateTransactionItemDTO.class, TransactionItem.class).addMapping(CreateTransactionItemDTO::getTransaction, TransactionItem::setTransaction);
    	return modelMapper.map(createTransactionItemDto, TransactionItem.class);        
    }

}
