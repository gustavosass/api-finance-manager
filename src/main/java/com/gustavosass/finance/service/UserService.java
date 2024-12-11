package com.gustavosass.finance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gustavosass.finance.exceptions.ObjectAlreadyExistsException;
import com.gustavosass.finance.exceptions.ObjectNotFoundException;
import com.gustavosass.finance.model.User;
import com.gustavosass.finance.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("User not found."));
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException("User not found."));
    }

    public User create(User user) {
    	if (existsByUsername(user.getUsername())) throw new ObjectAlreadyExistsException("This username already exists.");
    	
        return userRepository.save(user);
    }

    public User update(Long id, User user) {

        User userExists = findById(id);

        validateChangeUsername(user, userExists);
        
    	userExists.setFullName(user.getFullName());
    	userExists.setUsername(user.getUsername());
        return userRepository.save(userExists);
    }

    public void delete(Long id) {
    	findById(id);
        userRepository.deleteById(id);
    }
    
    private void validateChangeUsername(User user, User userExists) {
    	
    	//Valida se usernames são diferentes
        if (!userExists.getUsername().equals(user.getUsername())) {
        	
        	//Valida se novo username está disponivel
        	if (existsByUsername(user.getUsername())) throw new ObjectAlreadyExistsException("This username already exists.");
        }
    }
    
}
