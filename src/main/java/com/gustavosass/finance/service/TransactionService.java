package com.gustavosass.finance.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.gustavosass.finance.exceptions.FoundItemsPaidForTransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gustavosass.finance.model.Account;
import com.gustavosass.finance.model.Transaction;
import com.gustavosass.finance.model.TransactionItem;
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
		Transaction newTransaction = transactionRepository.save(transaction);
		createTransactionsItems(transaction);
		return newTransaction;
	}
	
	public Transaction update(Long id, Transaction transaction) {
		Transaction transactionExists = transactionRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Transaction not found."));
		Account accountExists = accountService.findById(transaction.getAccount().getId());
		
		abortIfAnyItemsPaidForTransaction(transaction.getId());
		transactionItemService.findAllById(id).stream().forEach((item) -> transactionItemService.delete(id, item.getId()));
		createTransactionsItems(transaction);
		
		transactionExists.setAccount(accountExists);
		transactionExists.setInstallmentNumbers(transaction.getInstallmentNumbers());
		transactionExists.setValue(transaction.getValue());
		transactionExists.setAccountingEntryType(transaction.getAccountingEntryType());
		
		return transactionRepository.save(transactionExists);
	}
	
	public void delete(Long id) {
		transactionRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Transaction not found."));
		abortIfAnyItemsPaidForTransaction(id);
		transactionRepository.deleteById(id);
	}
	
	private void createTransactionsItems(Transaction transaction){
		Double valueInstallment = transaction.getValue()/transaction.getInstallmentNumbers();
        for (int i=0;i<transaction.getInstallmentNumbers();i++){
            TransactionItem transactionItem = new TransactionItem();
            transactionItem.setValue(valueInstallment);
            transactionItem.setDueDate(transaction.getDueDate());
            transactionItem.setInstallmentNumber(i+1);
            transactionItem.setTransaction(transaction);
            transactionItemService.create(transactionItem);
        }
	}
	
	private void abortIfAnyItemsPaidForTransaction(Long idTransaction) {
		if(transactionRepository.existsItemsPaidForTransaction(idTransaction)) {
			throw new FoundItemsPaidForTransactionException("You haven't updated because you have paid items.");
		}
	}
}
