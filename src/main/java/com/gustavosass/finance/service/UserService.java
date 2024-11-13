package com.gustavosass.finance.service;

import com.gustavosass.finance.model.User;
import com.gustavosass.finance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));

    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found."));
    }

    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateKeyException("This username already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User update(Long id, User user) {

        Optional<User> userExists = userRepository.findById(id);

        if (userExists.isPresent()) {
            user.setId(userExists.get().getId());
            user.setPassword(userExists.get().getPassword());
            return userRepository.save(user);
        }
        throw new HttpClientErrorException(HttpStatus.NOT_MODIFIED, "Error updating user.");
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
