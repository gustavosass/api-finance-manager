package com.gustavosass.finance.controller;

import com.gustavosass.finance.dtos.RegisterUserDTO;
import com.gustavosass.finance.dtos.UserDTO;
import com.gustavosass.finance.mapper.UserMapper;
import com.gustavosass.finance.model.User;
import com.gustavosass.finance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<UserDTO>> getAll(){
        List<UserDTO> usersDto = userService.getAll().stream().map(userMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(usersDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        UserDTO userDto = userMapper.toDto(userService.findById(id));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody RegisterUserDTO registerUserDto) {
        User user = userMapper.toEntity(registerUserDto);
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
