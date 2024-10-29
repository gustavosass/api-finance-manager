package com.gustavosass.finance.controller;

import com.gustavosass.finance.dtos.UserDto;
import com.gustavosass.finance.mapper.UserMapper;
import com.gustavosass.finance.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<UserDto>> getAll(){
        List<UserDto> usersDto = userService.getAll().stream().map(userMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(usersDto);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> authenticatedUser() {
        return ResponseEntity.ok().build();
    }
}
