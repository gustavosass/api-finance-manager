package com.gustavosass.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gustavosass.finance.model.Transaction;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT (COUNT(t) > 0) FROM TransactionItem t where t.status = 'PAID' and t.transaction.id = ?1")
    Boolean existsItemsPaidForTransaction(Long id);
}
