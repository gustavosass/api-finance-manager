package com.gustavosass.finance.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.JpaCountQueryCreator;
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
		return accountRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Account not found."));
	}
	
	public Account create(Account account) {
		Set<User> usersExists = account.getUsers().stream().map(user -> userService.findById(user.getId())).collect(Collectors.toSet());
		account.setUsers(usersExists);
		return  accountRepository.save(account);
	}
	
	public Account update(Long id, Account account) {
		Account accountExists = accountRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Account not found."));
		Set<User> usersExists = account.getUsers().stream().map(user -> userService.findById(user.getId())).collect(Collectors.toSet());
		accountExists.setName(account.getName());
		accountExists.setUsers(usersExists);
		return accountRepository.save(accountExists);
	}
	
	public void delete(Long id) {
		accountRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Account not found."));
		accountRepository.deleteById(id);
	}
}
