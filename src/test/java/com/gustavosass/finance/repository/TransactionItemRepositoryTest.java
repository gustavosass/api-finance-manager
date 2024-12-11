package com.gustavosass.finance.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.gustavosass.finance.enums.AccountingEntryTypeEnum;
import com.gustavosass.finance.enums.PaymentStatusEnum;
import com.gustavosass.finance.model.Account;
import com.gustavosass.finance.model.Transaction;
import com.gustavosass.finance.model.TransactionItem;
import com.gustavosass.finance.model.User;

@DataJpaTest
@ActiveProfiles("test")
class TransactionItemRepositoryTest {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private TransactionItemRepository transactionItemRepository;
	
	private TransactionItem transactionItem;

	@BeforeEach
	void setUp() throws Exception {
		User user = new User(1L, "User", "user", "123");
		User createdUser = userRepository.save(user);

		Account account = new Account(1L, "Mock Account", Set.of(createdUser));
		Account createdAccount = accountRepository.save(account);
		
		Transaction transaction = new Transaction(1L, 200.00, new Date(), 2, createdAccount, null, new Date(), new Date(),
				PaymentStatusEnum.OPEN, AccountingEntryTypeEnum.DEBIT);
		Transaction createdTransaction = transactionRepository.save(transaction);
		
		TransactionItem transactionItem = new TransactionItem(1L, 100.00, createdTransaction, new Date(), 1, null, PaymentStatusEnum.PAID,
				new Date(), new Date());
		this.transactionItem = transactionItemRepository.save(transactionItem);
	}

	@Test
	void testFindAllByTransactionId() {
		List<TransactionItem> items = transactionItemRepository.findAllByTransactionId(transactionItem.getTransaction().getId());
		Assertions.assertEquals(1, items.size());
		Assertions.assertEquals(100.00, items.get(0).getValue());
	}
	
	@Test
	void testListInstallmentsPaidByTransactionId() {
		List<TransactionItem> items = transactionItemRepository.listInstallmentsPaidByTransactionId(transactionItem.getTransaction().getId());
		Assertions.assertEquals(1, items.size());
		Assertions.assertEquals(100.00, items.get(0).getValue());
		Assertions.assertEquals(PaymentStatusEnum.PAID, items.get(0).getStatus());
	}
	
	@Test
	void testExistsInstallmentsPaidByIdTransaction() {
		Boolean items = transactionItemRepository.existsInstallmentsPaidByIdTransaction(transactionItem.getTransaction().getId());
		Assertions.assertTrue(items);
	}
	

}
