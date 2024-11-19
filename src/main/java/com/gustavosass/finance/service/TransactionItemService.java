package com.gustavosass.finance.service;

import com.gustavosass.finance.model.Transaction;
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
    
    public List<TransactionItem> listInstallmentsPaidByIdTransaction(Long idTransaction) {
        return transactionItemRepository.listInstallmentsPaidByIdTransaction(idTransaction);
    }

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

    public void createTransactionsItems(Transaction transaction){
        Double valueInstallment = transaction.getValue()/transaction.getInstallmentNumbers();
        for (int i=0;i<transaction.getInstallmentNumbers();i++){
            TransactionItem transactionItem = new TransactionItem();
            transactionItem.setValue(valueInstallment);
            transactionItem.setDueDate(transaction.getDueDate());
            transactionItem.setInstallmentNumber(i+1);
            transactionItem.setTransaction(transaction);
            this.create(transactionItem);
        }
    }

}
