package com.gustavosass.finance.service;

import com.gustavosass.finance.enums.AccountingEntryTypeEnum;
import com.gustavosass.finance.enums.PaymentStatusEnum;
import com.gustavosass.finance.enums.RoleEnum;
import com.gustavosass.finance.model.Account;
import com.gustavosass.finance.model.Transaction;
import com.gustavosass.finance.model.TransactionItem;
import com.gustavosass.finance.model.User;
import com.gustavosass.finance.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionItemService transactionItemService;

    @Mock
    private AccountService accountService;

    @Autowired
    @InjectMocks
    private TransactionService transactionService;
    
    private User user;
    private Account account;
    private Transaction transaction;
    private List<TransactionItem> transactionItems = new ArrayList<>(); 

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        this.user = new User(1L,"User", "user", "123", Set.of(RoleEnum.SUPER_ADMIN));
        this.account = new Account(1L, "Mock Account", Set.of(user));
        this.transaction = new Transaction(1L, 200.00, new Date(), 2, account, null, new Date(), new Date(), PaymentStatusEnum.OPEN, AccountingEntryTypeEnum.DEBIT);
        this.transactionItems.add(new TransactionItem(1L, 100.00, transaction, new Date(), 1, null, PaymentStatusEnum.OPEN, new Date(), new Date()));
        this.transactionItems.add(new TransactionItem(2L, 100.00, transaction, new Date(), 2, null, PaymentStatusEnum.OPEN, new Date(), new Date()));
    }

    @Test
    @DisplayName("Validation create Transaction.")
    void createTransactionsAndCreateItems() {
        
        when(accountService.findById(1L)).thenReturn(account);
        
        transactionService.create(transaction);

        verify(transactionRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Update Transaction when installment and value is not updated.")
    void updateTransactionWhenInstallmentAndValueIsNotUpdated() {

    	when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        when(accountService.findById(1L)).thenReturn(account);
        
        transactionService.update(1L, transaction);

        verify(transactionRepository, times(1)).save(any());
    }

}