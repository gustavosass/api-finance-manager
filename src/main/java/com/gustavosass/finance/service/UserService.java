package com.gustavosass.finance.service;

import com.gustavosass.finance.model.Role;
import com.gustavosass.finance.model.User;
import com.gustavosass.finance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(NoSuchElementException::new);
    }

    public User create(User user){
        if (userRepository.existsByUsername(user.getUsername())){
            throw new HttpClientErrorException(HttpStatus.CONFLICT, "This username already exists.");
        }
        Role role = roleService.findByName(user.getRole().getName());

        return userRepository.save(user);
    }

}
