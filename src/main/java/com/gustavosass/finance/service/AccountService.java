package com.gustavosass.finance.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return accountRepository.findById(id).orElseThrow(NoSuchElementException::new);
	}
	
	public Account create(Account account) {
		Set<User> usersExists = account.getUsers().stream().map(user -> userService.findById(user.getId())).collect(Collectors.toSet());
		return  accountRepository.save(account);
	}
	
	public Account update(Long id, Account account) {
		Account accountExists = accountRepository.findById(id).orElseThrow(NoSuchElementException::new);
		accountExists.setName(account.getName());
		accountExists.setUsers(account.getUsers());
		accountExists.setBalance(account.getBalance());
		return accountRepository.save(accountExists);
	}
	
	public void delete(Long id) {
		accountRepository.deleteById(id);
	}
}
