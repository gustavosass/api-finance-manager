package com.gustavosass.finance.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import com.gustavosass.finance.enums.AccountingEntryTypeEnum;
import com.gustavosass.finance.enums.PaymentStatusEnum;
import com.gustavosass.finance.exceptions.FoundItemsPaidForTransactionException;
import com.gustavosass.finance.exceptions.NullArgumentException;
import com.gustavosass.finance.model.Account;
import com.gustavosass.finance.model.Transaction;
import com.gustavosass.finance.model.TransactionItem;
import com.gustavosass.finance.model.User;
import com.gustavosass.finance.repository.TransactionRepository;

@ActiveProfiles("test")
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionItemService transactionItemService;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionService transactionService;
    
    private User user;
    private Account account;
    private Transaction transaction;
    private List<TransactionItem> transactionItems = new ArrayList<>(); 

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        this.user = new User(1L, "User", "user", "123", true, true, true, true, Set.of(), new Date(), new Date());
        this.account = new Account(1L, "Mock Account", Set.of(user));
        this.transaction = new Transaction(1L, 200.00, new Date(), 2, account, null, new Date(), new Date(), PaymentStatusEnum.OPEN, AccountingEntryTypeEnum.DEBIT);
        this.transactionItems.add(new TransactionItem(1L, 100.00, transaction, new Date(), 1, null, PaymentStatusEnum.OPEN, new Date(), new Date()));
        this.transactionItems.add(new TransactionItem(2L, 100.00, transaction, new Date(), 2, null, PaymentStatusEnum.OPEN, new Date(), new Date()));
    }

    @Test
    void testFindAll() {
    	when(transactionRepository.findAll()).thenReturn(Collections.singletonList(transaction));
    	
    	List<Transaction> list = transactionService.findAll();
    	
    	Assertions.assertEquals(Transaction.class, list.get(0).getClass());
    	
    	verify(transactionRepository, times(1)).findAll();
    }
        
    @Test
    void testFindById() {
    	
    	when(transactionRepository.findById(transaction.getId())).thenReturn(Optional.of(transaction));
    	
    	Transaction entity = transactionService.findById(transaction.getId());
    	
    	Assertions.assertEquals(entity.getClass(), transaction.getClass());
    	verify(transactionRepository, times(1)).findById(any());
    }
    
    @Test
    void testFindByIdIfNotExists() {
    	
    	NoSuchElementException e = Assertions.assertThrows(NoSuchElementException.class, () -> {
        	transactionService.findById(transaction.getId());
    	});
    	Assertions.assertEquals(e.getMessage(), "Transaction not found.");    	
    }
    
    @Test
    void testCreate() {
        
        when(accountService.findById(1L)).thenReturn(account);
        
        transactionService.create(transaction);

        verify(transactionRepository, times(1)).save(any());
    }
    
    @Test
    void testCreateWithNullTransaction() {
    	    	
    	NullArgumentException e = Assertions.assertThrows(NullArgumentException.class, () -> {
        	transactionService.create(null);
    	});
    	
    	Assertions.assertEquals("Error processing with null body.", e.getMessage());
    	verify(transactionRepository, times(0)).save(any());
    }
    
    @Test
    void testUpdate() {

    	when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        when(accountService.findById(1L)).thenReturn(account);
        
        transactionService.update(1L, transaction);

        verify(transactionRepository, times(1)).save(any());
    }
    
    @Test
    void testUpdateIfAnyInstallmentsArePaid() {
    	
    	Transaction transactionDb = new Transaction(2L, 300.00, new Date(), 2, account, null, new Date(), new Date(), PaymentStatusEnum.OPEN, AccountingEntryTypeEnum.DEBIT);
    	
    	transactionItems.get(0).setStatus(PaymentStatusEnum.PAID);
    	
    	when(accountService.findById(1L)).thenReturn(account);
    	when(transactionRepository.findById(transactionDb.getId())).thenReturn(Optional.of(transactionDb));
    	when(transactionItemService.existsInstallmentsPaidByIdTransaction(transaction.getId())).thenReturn(true);
    	
    	FoundItemsPaidForTransactionException e = Assertions.assertThrows(FoundItemsPaidForTransactionException.class, () -> {
    		transactionService.update(2L, transaction);    		
    	});
    	
    	Assertions.assertEquals("Founded installments paid, please cancel installments for exclude.", e.getMessage());
        Assertions.assertEquals(FoundItemsPaidForTransactionException.class, e.getClass());
        
        verify(transactionRepository, times(0)).save(any());
    }
    
    @Test
    void testUpdateWithNullTransaction() {
        
    	NullArgumentException e = Assertions.assertThrows(NullArgumentException.class, () -> {
        	transactionService.update(1L, null);
    	});
    	
    	Assertions.assertEquals("Error processing with null body.", e.getMessage());
    	verify(transactionRepository, times(0)).save(any());
    }
    
    @Test
    void testDelete() {
    	when(transactionRepository.findById(transaction.getId())).thenReturn(Optional.of(transaction));
    	
    	transactionService.delete(transaction.getId());
    	
    	verify(transactionRepository, times(1)).findById(transaction.getId());
    	verify(transactionRepository, times(1)).deleteById(transaction.getId());
    }

    

}