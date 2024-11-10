package com.gustavosass.finance.mapper;

import com.gustavosass.finance.dtos.CreateTransactionDTO;
import com.gustavosass.finance.dtos.TransactionDTO;
import com.gustavosass.finance.dtos.TransactionItemDTO;
import com.gustavosass.finance.dtos.UpdateTransactionDTO;
import com.gustavosass.finance.model.Transaction;
import com.gustavosass.finance.model.TransactionItem;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionItemMapper {

    @Autowired
    private ModelMapper modelMapper;

    public TransactionItemDTO toDto(TransactionItem transactionItem) {
        return modelMapper.map(transactionItem, TransactionItemDTO.class);
    }

    public TransactionItem toEntity(TransactionItemDTO transactionItemDTO) {
        return modelMapper.map(transactionItemDTO, TransactionItem.class);
    }

}
