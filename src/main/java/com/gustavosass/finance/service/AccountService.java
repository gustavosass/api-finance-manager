package com.gustavosass.finance.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gustavosass.finance.exceptions.NullArgumentException;
import com.gustavosass.finance.exceptions.ObjectNotFoundException;
import com.gustavosass.finance.model.Account;
import com.gustavosass.finance.model.User;
import com.gustavosass.finance.repository.AccountRepository;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserService userService;
	
	public List<Account> findAll() {
		return accountRepository.findAll();
	}
	
	public Account findById(Long id) {
		return accountRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Account not found."));
	}
	
	public Account create(Account account) {
		
		if(account == null) throw new NullArgumentException();

		Set<User> usersExists = account.getUsers().stream().map(user -> userService.findById(user.getId())).collect(Collectors.toSet());
		account.setUsers(usersExists);
		
		return  accountRepository.save(account);
	}
	
	public Account update(Long id, Account account) {
		
		if(account == null || id == null) throw new NullArgumentException();
		
		Account accountExists = findById(id);
		Set<User> usersExists = account.getUsers().stream().map(user -> userService.findById(user.getId())).collect(Collectors.toSet());
		
		accountExists.setName(account.getName());
		accountExists.setUsers(usersExists);
		
		return accountRepository.save(accountExists);
	}
	
	public void delete(Long id) {
		findById(id);
		accountRepository.deleteById(id);
	}
}
