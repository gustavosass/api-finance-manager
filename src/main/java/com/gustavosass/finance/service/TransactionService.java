package com.gustavosass.finance.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import com.gustavosass.finance.dtos.AccountDTO;
import com.gustavosass.finance.enums.AccountingEntryTypeEnum;
import com.gustavosass.finance.enums.PaymentStatusEnum;
import com.gustavosass.finance.exceptions.FoundItemsPaidForTransactionException;
import com.gustavosass.finance.model.TransactionItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;

import com.gustavosass.finance.model.Account;
import com.gustavosass.finance.model.Transaction;
import com.gustavosass.finance.repository.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
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
		return transactionRepository.save(transaction);
	}
	
	public Transaction update(Long id, Transaction transaction) {
		Transaction transactionExists = transactionRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Transaction not found."));
		Account accountExists = accountService.findById(transaction.getAccount().getId());
		transactionExists.setAccount(accountExists);
		System.out.println(transactionRepository.existsItemsPaidForTransaction(transactionExists.getId()));
		if (transactionRepository.existsItemsPaidForTransaction(transactionExists.getId())){
            throw new FoundItemsPaidForTransactionException("You haven't updated because you have paid items.");
		}
		transactionExists.setInstallmentNumbers(transaction.getInstallmentNumbers());
		transactionExists.setValue(transaction.getValue());
		transactionExists.setAccountingEntryType(transaction.getAccountingEntryType());
		return transactionRepository.save(transactionExists);
	}
	
	public void delete(Long id) {
		transactionRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Transaction not found."));
		transactionRepository.deleteById(id);
	}
}
