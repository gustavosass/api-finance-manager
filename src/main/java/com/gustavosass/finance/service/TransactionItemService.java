package com.gustavosass.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gustavosass.finance.exceptions.ObjectNotFoundException;
import com.gustavosass.finance.model.TransactionItem;
import com.gustavosass.finance.repository.TransactionItemRepository;

@Service
public class TransactionItemService {

	@Autowired
	private TransactionItemRepository transactionItemRepository;

	public boolean existsInstallmentsPaidByIdTransaction(Long id) {
		return transactionItemRepository.existsInstallmentsPaidByIdTransaction(id);
	}

	public List<TransactionItem> listInstallmentsPaidByIdTransaction(Long id) {
		return transactionItemRepository.listInstallmentsPaidByTransactionId(id);
	}

	public List<TransactionItem> findAllByTransactionId(Long id) {
		return transactionItemRepository.findAllByTransactionId(id);
	}

	public TransactionItem findById(Long id) {
		return transactionItemRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Item not found."));
	}

	public TransactionItem create(TransactionItem item) {
		return transactionItemRepository.save(item);
	}

	public TransactionItem update(Long id, TransactionItem item) {

		TransactionItem transactionExists = findById(id);

		transactionExists.setDatePayment(item.getDatePayment());
		transactionExists.setDueDate(item.getDueDate());
		transactionExists.setStatus(item.getStatus());
		transactionExists.setValue(item.getValue());

		return transactionItemRepository.save(item);
	}

	public void delete(Long id) {
		findById(id);
		transactionItemRepository.deleteById(id);
	}

}
