package com.gustavosass.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gustavosass.finance.model.TransactionItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionItemRepository extends JpaRepository<TransactionItem, Long> {
    List<TransactionItem> findAllByTransactionId(Long id);

    @Query("SELECT t FROM TransactionItem t where t.status = 'PAID' and t.transaction.id = ?1")
    List<TransactionItem> listInstallmentsPaidByTransactionId(Long id);
    
    @Query("SELECT COUNT(t) > 0 FROM TransactionItem t where t.status = 'PAID' and t.transaction.id = ?1")
    boolean existsInstallmentsPaidByIdTransaction(Long id);
}
