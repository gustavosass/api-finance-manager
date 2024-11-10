package com.gustavosass.finance.service;

import com.gustavosass.finance.enums.PaymentStatusEnum;
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

    @Autowired
    private TransactionService transactionService;

    public List<TransactionItem> findAllById(Long idTransaction) {
        return transactionItemRepository.findAllByTransactionId(idTransaction);
    }

    public TransactionItem findById(Long idTransaction, Long id) {
        return transactionItemRepository.findIdByTransactionIdAndId(idTransaction, id).orElseThrow(() -> new NoSuchElementException("Item not found."));
    }

    public TransactionItem create(Long idTransaction, TransactionItem transactionItem) {
        Transaction transaction = transactionService.findById(idTransaction);
        transactionItem.setTransaction(transaction);
        return transactionItemRepository.save(transactionItem);
    }

    public TransactionItem update(Long idTransaction, Long idItem, TransactionItem transactionItem) {
        TransactionItem transactionItemExists = this.findById(idTransaction, idItem);
        transactionService.findById(idTransaction);

        if (transactionItem.getDatePayment() == null) {
            transactionItemExists.setStatus(PaymentStatusEnum.OPEN);
            transactionItemExists.setDatePayment(null);
        }else{
            transactionItemExists.setStatus(PaymentStatusEnum.PAID);
            transactionItemExists.setDatePayment(transactionItem.getDatePayment());
        }
        return transactionItemRepository.save(transactionItemExists);
    }

    public void delete(Long idTransaction, Long id){
        this.findById(idTransaction, id);
        transactionItemRepository.deleteById(id);
    }
}
