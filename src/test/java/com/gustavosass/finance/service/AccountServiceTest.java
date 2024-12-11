package com.gustavosass.finance.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
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

import com.gustavosass.finance.exceptions.NullArgumentException;
import com.gustavosass.finance.exceptions.ObjectNotFoundException;
import com.gustavosass.finance.model.Account;
import com.gustavosass.finance.model.User;
import com.gustavosass.finance.repository.AccountRepository;
import com.gustavosass.finance.repository.UserRepository;

@ActiveProfiles("test")
class AccountServiceTest {

	@Mock
	private AccountRepository accountRepository;
	
	@Mock
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private AccountService accountService;
	
	
	private User user;
    private Account account;
    
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
        this.user = new User(1L,"User", "user", "123");
        this.account = new Account(1L, "Mock Account", Set.of(user));
	}

	@Test
	void testFindAll() {
		when(accountService.findAll()).thenReturn(Collections.singletonList(account));
		
		List<Account> list = accountService.findAll();
		
		Assertions.assertEquals(account, list.get(0));
		verify(accountRepository, times(1)).findAll();
	}

	@Test
	void testFindById() {
		when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
		
		Account entity = accountService.findById(account.getId());
		
		Assertions.assertEquals(account.getId(), entity.getId());
		verify(accountRepository, times(1)).findById(any());
	}
	
	@Test
	void testFindByIdIfNotExists() {
		RuntimeException e = Assertions.assertThrows(RuntimeException.class, ()-> {
			accountService.findById(account.getId());
		});
		
		Assertions.assertEquals("Account not found.", e.getMessage());
		verify(accountRepository, times(1)).findById(any());
	}

	@Test
	void testCreate() {
		when(userService.findById(user.getId())).thenReturn(user);		
		when(accountRepository.save(account)).thenReturn(account);
		
		Account entity = accountService.create(account);
			 
		Assertions.assertEquals(account, entity);
		verify(accountRepository, times(1)).save(any());
	}
	
	@Test
	void testExceptionForCreateWhenUserNotFound() {
		
	    when(userService.findById(1L)).thenThrow(new ObjectNotFoundException("User not found."));
		
		ObjectNotFoundException e = Assertions.assertThrows(ObjectNotFoundException.class, ()->{
			accountService.create(account);
		});
				
		Assertions.assertEquals("User not found.", e.getMessage());
		verify(accountRepository, times(0)).save(any());
	}
	
	@Test
	void testExceptionForCreateWhenAccountIsNull() {
		
		NullArgumentException e = Assertions.assertThrows(NullArgumentException.class, ()->{
			accountService.create(null);
		});
				
		Assertions.assertEquals("Error processing with null body.", e.getMessage());
		verify(accountRepository, times(0)).save(any());
	}

	@Test
	void testUpdate() {
		
		Account updatedAccount = new Account(2L, "Mock Account2", Set.of(user));
		
		when(accountRepository.findById(1L)).thenReturn(Optional.of(updatedAccount));
		when(userService.findById(user.getId())).thenReturn(user);		
	    when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Account entity = accountService.update(1L, account);
		
		Assertions.assertEquals(account.getName(), entity.getName());
		Assertions.assertEquals(account.getUsers(), entity.getUsers());
		
		verify(accountRepository, times(1)).save(any());
	}
	
	@Test
	void testExceptionForUpdateIfChangeUserNotFound() {
		
		Account updatedAccount = new Account(2L, "Mock Account2", Set.of(user));
		
		when(accountRepository.findById(1L)).thenReturn(Optional.of(updatedAccount));
		when(userService.findById(user.getId())).thenThrow(new ObjectNotFoundException("User not found."));
		
		ObjectNotFoundException e = Assertions.assertThrows(ObjectNotFoundException.class, () -> {
			accountService.update(1L, account);
		});
		
		Assertions.assertEquals("User not found.", e.getMessage());
		verify(accountRepository, times(0)).save(any());
	}
	
	@Test
	void testExceptionForUpdateWhenAccountIsNull() {
				
		NullArgumentException e = Assertions.assertThrows(NullArgumentException.class, () -> {
			accountService.update(1L, null);	
		});
		
		Assertions.assertEquals("Error processing with null body.", e.getMessage());
		verify(accountRepository, times(0)).save(any());
	}

	@Test
	void testDelete() {
		when(accountRepository.findById(account.getId())).thenReturn(Optional.of(account));
		
		accountService.delete(account.getId());
		
		verify(accountRepository, times(1)).findById(any());
		verify(accountRepository, times(1)).deleteById(any());
	}
	
	@Test
	void testExceptionForDeleteIfNotExistsAccount() {		
		
		ObjectNotFoundException e = Assertions.assertThrows(ObjectNotFoundException.class, () -> {
			accountService.delete(account.getId());			
		});
		
		Assertions.assertEquals("Account not found.", e.getMessage());
		verify(accountRepository, times(1)).findById(any());
	}
} 
