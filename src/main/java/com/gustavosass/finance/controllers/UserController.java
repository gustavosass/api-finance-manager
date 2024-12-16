package com.gustavosass.finance.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gustavosass.finance.dtos.CreateUserDTO;
import com.gustavosass.finance.dtos.UserDTO;
import com.gustavosass.finance.mapper.UserMapper;
import com.gustavosass.finance.model.User;
import com.gustavosass.finance.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user")
@Tag(name = "User")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll(){
        List<UserDTO> usersDto = userService.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(usersDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        UserDTO userDto = userMapper.toDto(userService.findById(id));
        return ResponseEntity.ok(userDto);
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<UserDTO> create(@RequestBody CreateUserDTO createUserDto) {
        User user = userMapper.toEntity(createUserDto);
        User userCreated = userService.create(user);
        return ResponseEntity.ok(userMapper.toDto(userCreated));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO userDto){
        User user = userMapper.toEntity(userDto);
        User userCreated = userService.update(id, user);
        return ResponseEntity.ok(userMapper.toDto(userCreated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
