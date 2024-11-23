package com.gustavosass.finance.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gustavosass.finance.exceptions.FoundItemsPaidForTransactionException;
import com.gustavosass.finance.exceptions.NullArgumentException;
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
	
	public Transaction create(Transaction transaction){
		if (transaction == null) throw new NullArgumentException();

		Account account = accountService.findById(transaction.getAccount().getId());
		transaction.setAccount(account);
		
		return transactionRepository.save(transaction);
	}

	public Transaction update(Long id, Transaction transaction) {
		if (transaction == null || id == null) throw new NullArgumentException();
		
		Transaction transactionExists = transactionRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Transaction not found."));

		Account accountExists = accountService.findById(transaction.getAccount().getId());
		
		//Valida se houve mudança no valor ou número de parcelas.
		validateTransactionChange(transaction, transactionExists);
		
		transactionExists.setAccount(accountExists);
		transactionExists.setInstallmentNumbers(transaction.getInstallmentNumbers());
		transactionExists.setValue(transaction.getValue());
		transactionExists.setAccountingEntryType(transaction.getAccountingEntryType());
		
		return transactionRepository.save(transactionExists);
	}
	
	public void delete(Long id) {
		transactionRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Transaction not found."));
		transactionRepository.deleteById(id);
	}
	
	private void validateTransactionChange(Transaction transaction, Transaction transactionExists) {
		
		if (compareInstallmentNumberIsNotEquals(transaction, transactionExists) ||  compareValueIsNotEquals(transaction, transactionExists)) {
			
			//Valida se possui alguma parcela paga.
			if (transactionItemService.existsInstallmentsPaidByIdTransaction(transaction.getId())) {
				throw new FoundItemsPaidForTransactionException("Founded installments paid, please cancel installments for exclude.");
			}
			
			transactionItemService.findAllByTransactionId(transactionExists.getId()).forEach((item) -> transactionItemService.delete(item.getId()));
		}
	}

	private Boolean compareValueIsNotEquals(Transaction transaction, Transaction transactionExists) {
		return !transaction.getValue().equals(transactionExists.getValue());
	}
	
	private Boolean compareInstallmentNumberIsNotEquals(Transaction transaction, Transaction transactionExists) {
		return transactionExists.getInstallmentNumbers() != transaction.getInstallmentNumbers();
	}
	
}
