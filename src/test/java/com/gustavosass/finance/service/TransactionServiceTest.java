package com.gustavosass.finance.service;

import com.gustavosass.finance.enums.AccountingEntryTypeEnum;
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

import java.util.*;

import static org.mockito.Mockito.*;

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

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Validation create Transaction and TransactionItems.")
    void create() {
        User user = new User(1L,"User", "user", "123", Set.of(RoleEnum.SUPER_ADMIN));
        Account account = new Account(1L, "Mock Account", Set.of(user));
        Transaction transaction = createTransaction(account);

        //Mock account
        when(accountService.findById(1L)).thenReturn(account);
        //Mock return of save transaction
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        transactionService.create(transaction);

        //Validate save transaction
        verify(transactionRepository, times(1)).save(any());
        //Validate create 10 items.
        verify(transactionItemService, times(1)).createTransactionsItems(any());
    }

    @Test
    @DisplayName("Validation update Transaction and TransactionItems.")
    void update() {
        User user = new User(1L,"Gustavo", "gustavo", "123", Set.of(RoleEnum.SUPER_ADMIN));
        Account account = new Account(1L, "Mock Account", Set.of(user));
        Transaction transaction = createTransaction(account);
        List<TransactionItem> transactionItems = createItems(transaction);

        //Mock Transaction
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        //Mock account
        when(accountService.findById(1L)).thenReturn(account);
        //Mock TransactionItem findAll
        when(transactionItemService.findAllById(1L)).thenReturn(transactionItems);

        transactionService.update(1L, transaction);

        //Validate delete 2 items.
        verify(transactionItemService, times(2)).delete(any(),any());
        //Validate create 2 items.
        verify(transactionItemService, times(1)).createTransactionsItems(any());
        //Validate save transaction
        verify(transactionRepository, times(1)).save(any());
    }

    private List<TransactionItem> createItems(Transaction transaction){
        TransactionItem item1 = new TransactionItem();
        item1.setValue(100.00);
        item1.setDueDate(transaction.getDueDate());
        item1.setInstallmentNumber(1);
        item1.setTransaction(transaction);

        TransactionItem item2 = new TransactionItem();
        item2.setValue(100.00);
        item2.setDueDate(transaction.getDueDate());
        item2.setInstallmentNumber(2);
        item2.setTransaction(transaction);

        return List.of(item1, item2);
    }

    private Transaction createTransaction(Account account) {
        Transaction transaction = new Transaction();
        transaction.setValue(100.00);
        transaction.setInstallmentNumbers(2);
        transaction.setAccount(account);
        transaction.setAccountingEntryType(AccountingEntryTypeEnum.DEBIT);
        transaction.setDueDate(new Date());
        return transaction;
    }

}