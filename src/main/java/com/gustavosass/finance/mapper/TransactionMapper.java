package com.gustavosass.finance.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gustavosass.finance.dtos.CreateTransactionDTO;
import com.gustavosass.finance.dtos.TransactionDTO;
import com.gustavosass.finance.model.Transaction;

@Component
public class TransactionMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	public TransactionDTO toDto(Transaction transaction) {
		return modelMapper.map(transaction, TransactionDTO.class);
	}
	
	public Transaction toEntity(TransactionDTO transactionDTO) {
		return modelMapper.map(transactionDTO, Transaction.class);
	}
	
	public Transaction toEntity(CreateTransactionDTO createTransactionDTO) {
		return modelMapper.map(createTransactionDTO, Transaction.class);
	}

}
