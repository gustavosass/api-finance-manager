package com.gustavosass.finance.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gustavosass.finance.model.Account;
import com.gustavosass.finance.model.Transaction;
import com.gustavosass.finance.repository.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private TransactionItemService transactionItemService;
	
	@Autowired
	private AccountService accountService;
	
	public List<Transaction> findAll() {
		return transactionRepository.findAll();
	}
	
	public Transaction findById(Long id) {
		return transactionRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Transaction not found."));
	}
	
	public Transaction create(Transaction transaction) {
		Account account = accountService.findById(transaction.getAccount().getId());

		transaction.setAccount(account);
		Transaction transactionCreated = transactionRepository.save(transaction);

		transactionItemService.createTransactionsItems(transactionCreated);

		return transactionCreated;
	}

	public Transaction update(Long id, Transaction transaction) {
		Transaction transactionExists = transactionRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Transaction not found."));
		Account accountExists = accountService.findById(transaction.getAccount().getId());

		transactionItemService.existsItemsPaidForTransaction(transaction.getId());
		transactionItemService.findAllById(id).stream().forEach((item) -> transactionItemService.delete(id, item.getId()));
		transactionItemService.createTransactionsItems(transaction);
		
		transactionExists.setAccount(accountExists);
		transactionExists.setInstallmentNumbers(transaction.getInstallmentNumbers());
		transactionExists.setValue(transaction.getValue());
		transactionExists.setAccountingEntryType(transaction.getAccountingEntryType());
		
		return transactionRepository.save(transactionExists);
	}
	
	public void delete(Long id) {
		transactionRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Transaction not found."));
		transactionItemService.existsItemsPaidForTransaction(id);
		transactionRepository.deleteById(id);
	}
}
