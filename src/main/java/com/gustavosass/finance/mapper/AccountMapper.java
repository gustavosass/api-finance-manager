package com.gustavosass.finance.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gustavosass.finance.dtos.AccountDTO;
import com.gustavosass.finance.model.Account;

@Component
public class AccountMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	public Account toEntity(AccountDTO accountDto) {
		return modelMapper.map(accountDto, Account.class);
	}
	
	public AccountDTO toDto(Account account) {
		return modelMapper.map(account, AccountDTO.class);
	}
}
