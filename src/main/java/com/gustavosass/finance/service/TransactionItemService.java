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

    public TransactionItem findById(Long idTransaction, Long idItem) {
        return transactionItemRepository.findIdByTransactionIdAndId(idTransaction, idItem).orElseThrow(() -> new NoSuchElementException("Item not found."));
    }

    public void create(Transaction transaction) {
        Double valueInstallment = transaction.getValue()/transaction.getInstallmentNumbers();
        for (int i=0;i<transaction.getInstallmentNumbers();i++){
            System.out.println(i+1);
            TransactionItem transactionItem = new TransactionItem();
            transactionItem.setValue(valueInstallment);
            transactionItem.setDueDate(transaction.getDueDate());
            transactionItem.setInstallmentNumber(i+1);
            transactionItem.setTransaction(transaction);
            transactionItemRepository.save(transactionItem);
        }
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

    public void delete(Long idTransaction, Long idItem){
        this.findById(idTransaction, idItem);
        transactionItemRepository.deleteById(idItem);
    }
}
