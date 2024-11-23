package com.gustavosass.finance.service;

import static org.junit.jupiter.api.Assertions.fail;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.gustavosass.finance.enums.RoleEnum;
import com.gustavosass.finance.exceptions.ObjectAlreadyExistsException;
import com.gustavosass.finance.exceptions.ObjectNotFoundException;
import com.gustavosass.finance.model.User;
import com.gustavosass.finance.repository.UserRepository;

@ActiveProfiles("test")
class UserServiceTest {
	
	@Mock
    private UserRepository userRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private UserService userService;

	private User user;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
        this.user = new User(1L, "User", "user", "123", Set.of(RoleEnum.SUPER_ADMIN));
	}

	@Test
	void testFindAll() {
		when(userService.findAll()).thenReturn(Collections.singletonList(user));
		
		List<User> list = userService.findAll();
		
		Assertions.assertEquals(User.class, list.get(0).getClass());
		verify(userRepository, times(1)).findAll();
	}

	@Test
	void testFindById() {
		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		
		User entity = userService.findById(user.getId());
		
		Assertions.assertEquals(user, entity);
		verify(userRepository, times(1)).findById(any());
	}
	
	@Test
	void testFindByIdIfNotExists() {
		RuntimeException e = Assertions.assertThrows(RuntimeException.class, ()-> {
			userService.findById(user.getId());
		});
		
		Assertions.assertEquals("User not found.", e.getMessage());
		verify(userRepository, times(1)).findById(any());
	}

	@Test
	void testExistsByUsername() {
		
		when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);
		
		Boolean entity = userService.existsByUsername(user.getUsername());
		
		Assertions.assertTrue(entity);
		verify(userRepository, times(1)).existsByUsername(user.getUsername());
	}
	
	@Test
	void testExceptionForExistsByUsernameIfNotExists() {
		
		Boolean entity = userService.existsByUsername("");
		
		
		Assertions.assertFalse(entity);
		verify(userRepository, times(1)).existsByUsername(any());
	}

	@Test
	void testFindByUsername() {
		
		when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
		
		User entity = userService.findByUsername(user.getUsername());
		
		Assertions.assertEquals(user, entity);
		verify(userRepository, times(1)).findByUsername(user.getUsername());
	}
	
	@Test
	void testExceptionForFindByUsernameIfNotExists() {
		RuntimeException e = Assertions.assertThrows(RuntimeException.class, ()-> {
			userService.findByUsername(user.getUsername());
		});
				
		Assertions.assertEquals("User not found.", e.getMessage());
		verify(userRepository, times(1)).findByUsername(user.getUsername());
	}

	@Test
	void testCreate() {
		when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
		when(userRepository.save(user)).thenReturn(user);
		
		User entity = userService.create(user);
	
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		 
		Assertions.assertEquals(encodedPassword, entity.getPassword());
		Assertions.assertEquals(user, entity);
		verify(userRepository, times(1)).save(any());
	}
	
	@Test
	void testExceptionForCreateIfUserAlreadyExists() {
		
		when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);
		
		ObjectAlreadyExistsException e = Assertions.assertThrows(ObjectAlreadyExistsException.class, ()->{
			userService.create(user);
		});
				
		Assertions.assertEquals("This username already exists.", e.getMessage());
		verify(userRepository, times(0)).save(any());
	}

	@Test
	void testUpdate() {
		User updatedUser = new User(2L, "Abc", "abc", "123", Set.of(RoleEnum.ADMIN));
		
		when(userRepository.findById(1L)).thenReturn(Optional.of(updatedUser));
		when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
	    when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

		User entity = userService.update(1L, user);
		
		Assertions.assertEquals(updatedUser.getFullName(), entity.getFullName());
		Assertions.assertEquals(updatedUser.getUsername(), entity.getUsername());
		Assertions.assertEquals(updatedUser.getRoles(), entity.getRoles());
		
		verify(userRepository, times(1)).save(any());
	}
	
	@Test
	void testExceptionForUpdateIfChangeExistUsername() {
		User updatedUser = new User(2L, "Abc", "abc", "123", Set.of(RoleEnum.ADMIN));
		
		when(userRepository.findById(1L)).thenReturn(Optional.of(updatedUser));
		when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);
		
		ObjectAlreadyExistsException e = Assertions.assertThrows(ObjectAlreadyExistsException.class, () -> {
			userService.update(1L, user);			
		});
		
		Assertions.assertEquals("This username already exists.", e.getMessage());
		verify(userRepository, times(0)).save(any());
	}

	@Test
	void testDelete() {
		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		
		userService.delete(user.getId());
		
		verify(userRepository, times(1)).findById(any());
		verify(userRepository, times(1)).deleteById(any());
	}
	
	@Test
	void testExceptionForDeleteIfNotExistsUser() {		
		
		ObjectNotFoundException e = Assertions.assertThrows(ObjectNotFoundException.class, () -> {
			userService.delete(user.getId());			
		});
		
		Assertions.assertEquals("User not found.", e.getMessage());
		verify(userRepository, times(1)).findById(any());
	}
	

}
