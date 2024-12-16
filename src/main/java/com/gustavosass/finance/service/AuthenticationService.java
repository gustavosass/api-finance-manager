package com.gustavosass.finance.service;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gustavosass.finance.dtos.CreateUserDTO;
import com.gustavosass.finance.dtos.authenticate.LoginDTO;
import com.gustavosass.finance.model.User;
import com.gustavosass.finance.repository.UserRepository;

@Service
public class AuthenticationService {
	
	@Autowired
    private final UserRepository userRepository;
    
	@Autowired
    private final PasswordEncoder passwordEncoder;
    
	@Autowired
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        UserRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User authenticate(LoginDTO loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		loginDto.getUsername(),
                		loginDto.getPassword()
                )
        );

        return userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(loginDto.getUsername()));
    }
}
