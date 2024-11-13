package com.gustavosass.finance.service;

import com.gustavosass.finance.model.TransactionItem;
import com.gustavosass.finance.repository.TransactionItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TransactionItemService {

    @Autowired
    private TransactionItemRepository transactionItemRepository;

    public List<TransactionItem> findAllById(Long idTransaction) {
        return transactionItemRepository.findAllByTransactionId(idTransaction);
    }

    public TransactionItem findById(Long idTransaction, Long idItem) {
        return transactionItemRepository.findIdByTransactionIdAndId(idTransaction, idItem).orElseThrow(() -> new NoSuchElementException("Item not found."));
    }

    public TransactionItem create(TransactionItem transactionItem) {
    	return transactionItemRepository.save(transactionItem);
    }

    public void delete(Long idTransaction, Long idItem){
        transactionItemRepository.deleteById(idItem);
    }
    
}
