package com.gustavosass.finance.mapper;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gustavosass.finance.dtos.CreateUserDTO;
import com.gustavosass.finance.dtos.UserDTO;
import com.gustavosass.finance.model.Role;
import com.gustavosass.finance.model.User;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Component
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public User toEntity(UserDTO userDto){
        return modelMapper.map(userDto, User.class);
    }

    public UserDTO toDto(User user){
        return modelMapper.map(user, UserDTO.class);
    }
    
    public User toEntity(CreateUserDTO createUserDTO) {
    	Set<Role> roles = createUserDTO.getRoles().stream().map(role -> new Role(role)).collect(Collectors.toSet());
    	User user = new User();
    	user.setUsername(createUserDTO.getUsername());
    	user.setPassword(createUserDTO.getPassword());
    	user.setFullName(createUserDTO.getFullName());
    	user.setRoles(roles);
    	return user;
    }

}
