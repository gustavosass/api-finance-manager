package com.gustavosass.finance.security;

import com.gustavosass.finance.dtos.LoginUserDto;
import com.gustavosass.finance.dtos.RegisterUserDto;
import com.gustavosass.finance.model.Role;
import com.gustavosass.finance.model.User;
import com.gustavosass.finance.service.RoleService;
import com.gustavosass.finance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User signup(RegisterUserDto input) {
        Role roleExists = roleService.findByName(input.getRole().getName());

        User user = new User();
        user.setFullName(input.getFullName());
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole(roleExists);

        return userService.create(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        return userService.findByUsername(input.getUsername());
    }
}
