package com.gustavosass.finance.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Date;
import java.util.List;
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
import com.gustavosass.finance.enums.RoleEnum;
import com.gustavosass.finance.exceptions.NullArgumentException;
import com.gustavosass.finance.exceptions.ObjectNotFoundException;
import com.gustavosass.finance.model.Account;
import com.gustavosass.finance.model.Transaction;
import com.gustavosass.finance.model.TransactionItem;
import com.gustavosass.finance.model.User;
import com.gustavosass.finance.repository.TransactionItemRepository;

@ActiveProfiles("test")
class TransactionItemServiceTest {

	@Mock
	private TransactionItemRepository transactionItemRepository;

	@InjectMocks
	private TransactionItemService transactionItemService;

	private Transaction transaction;
	private TransactionItem transactionItem;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		User user = new User(1L, "User", "user", "123", Set.of(RoleEnum.SUPER_ADMIN));
		Account account = new Account(1L, "Mock Account", Set.of(user));
		this.transaction = new Transaction(1L, 200.00, new Date(), 2, account, null, new Date(), new Date(),
				PaymentStatusEnum.OPEN, AccountingEntryTypeEnum.DEBIT);
		this.transactionItem = new TransactionItem(1L, 100.00, transaction, new Date(), 1, null, PaymentStatusEnum.PAID,
				new Date(), new Date());
	}

	@Test
	void testExistsInstallmentsPaidByIdTransaction() {

		when(transactionItemRepository.existsInstallmentsPaidByIdTransaction(1L)).thenReturn(true);

		boolean exists = transactionItemService.existsInstallmentsPaidByIdTransaction(1L);
		Assertions.assertTrue(exists);
		verify(transactionItemRepository, times(1)).existsInstallmentsPaidByIdTransaction(any());
	}

	@Test
	void testNotExistsInstallmentsPaidByIdTransaction() {

		when(transactionItemRepository.existsInstallmentsPaidByIdTransaction(1L)).thenReturn(false);

		boolean exists = transactionItemService.existsInstallmentsPaidByIdTransaction(1L);
		Assertions.assertFalse(exists);
		verify(transactionItemRepository, times(1)).existsInstallmentsPaidByIdTransaction(any());
	}

	@Test
	void testListInstallmentsPaidByIdTransaction() {
		when(transactionItemRepository.listInstallmentsPaidByTransactionId(1L))
				.thenReturn(Collections.singletonList(transactionItem));

		List<TransactionItem> exists = transactionItemService.listInstallmentsPaidByIdTransaction(1L);

		Assertions.assertEquals(1, exists.size());
		verify(transactionItemRepository, times(1)).listInstallmentsPaidByTransactionId(any());
	}

	@Test
	void testFindAllByTransactionId() {
		when(transactionItemRepository.findAllByTransactionId(1L)).thenReturn(Collections.singletonList(transactionItem));

		List<TransactionItem> exists = transactionItemService.findAllByTransactionId(1L);

		Assertions.assertEquals(1, exists.size());
		verify(transactionItemRepository, times(1)).findAllByTransactionId(any());
	}

	@Test
	void testFindById() {
		when(transactionItemRepository.findAllByTransactionId(1L))
				.thenReturn(Collections.singletonList(transactionItem));

		List<TransactionItem> exists = transactionItemService.findAllByTransactionId(1L);

		Assertions.assertEquals(1, exists.size());
		verify(transactionItemRepository, times(1)).findAllByTransactionId(any());
	}

	@Test
	void testExceptionNotFoundWhenFindById() {
		when(transactionItemRepository.findAllByTransactionId(1L))
				.thenReturn(Collections.singletonList(transactionItem));

		List<TransactionItem> exists = transactionItemService.findAllByTransactionId(1L);

		Assertions.assertEquals(1, exists.size());
		verify(transactionItemRepository, times(1)).findAllByTransactionId(any());
	}

	@Test
	void testCreate() {
		when(transactionItemRepository.save(transactionItem)).thenReturn(transactionItem);

		TransactionItem createdItem = transactionItemService.create(transactionItem);

		Assertions.assertEquals(transactionItem, createdItem);
		verify(transactionItemRepository, times(1)).save(any());
	}

	@Test
	void testExceptionForNullCreate() {

		NullArgumentException e = Assertions.assertThrows(NullArgumentException.class, () -> {
			transactionItemService.create(null);
		});

		Assertions.assertEquals("Error processing with null body.", e.getMessage());
		verify(transactionItemRepository, times(0)).save(any());
	}

	@Test
	void testUpdate() {
		TransactionItem itemUpdate = new TransactionItem(1L, 200.00, transaction, new Date(), 1, new Date(), PaymentStatusEnum.PAID,
				new Date(), new Date());
		
		when(transactionItemRepository.findById(1L)).thenReturn(Optional.of(itemUpdate));

	    when(transactionItemRepository.save(any(TransactionItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

		TransactionItem updatedItem = transactionItemService.update(1L, transactionItem);
		
		Assertions.assertEquals(transactionItem.getDatePayment(), updatedItem.getDatePayment());
		Assertions.assertEquals(transactionItem.getDueDate(), updatedItem.getDueDate());
		Assertions.assertEquals(transactionItem.getStatus(), updatedItem.getStatus());
		Assertions.assertEquals(transactionItem.getValue(), updatedItem.getValue());
		
		verify(transactionItemRepository, times(1)).save(any());
	}
	
	@Test
	void testExceptionForNullUpdate() {
	
		NullArgumentException e = Assertions.assertThrows(NullArgumentException.class, () -> {
			transactionItemService.update(1L, null);
		});

		Assertions.assertEquals("Error processing with null body.", e.getMessage());
		verify(transactionItemRepository, times(0)).save(any());
	}

	@Test
	void testDelete() {
		when(transactionItemRepository.findById(1L)).thenReturn(Optional.of(transactionItem));
		
		transactionItemService.delete(1L);
		
		verify(transactionItemRepository, times(1)).deleteById(any());
	}
	
	@Test
	void testNotFoundIdForDelete() {
		
		ObjectNotFoundException e = Assertions.assertThrows(ObjectNotFoundException.class, () -> {
			transactionItemService.delete(1L);
		});
		
		Assertions.assertEquals("Item not found.", e.getMessage());
		verify(transactionItemRepository, times(0)).deleteById(any());
	}

}
