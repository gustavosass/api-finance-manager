package com.gustavosass.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gustavosass.finance.model.TransactionItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionItemRepository extends JpaRepository<TransactionItem, Long> {
    List<TransactionItem> findAllByTransactionId(Long id);

    Optional<TransactionItem> findIdByTransactionIdAndId(Long idTransaction, Long id);

}
