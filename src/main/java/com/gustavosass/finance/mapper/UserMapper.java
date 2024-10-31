package com.gustavosass.finance.mapper;

import com.gustavosass.finance.dtos.RegisterUserDTO;
import com.gustavosass.finance.dtos.UserDTO;
import com.gustavosass.finance.model.User;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public User toEntity(UserDTO userDto){
        return modelMapper.map(userDto, User.class);
    }

    public User toEntity(RegisterUserDTO registerUserDto){
        User user = new User();
        user.setFullName(registerUserDto.getFullName());
        user.setUsername(registerUserDto.getUsername());
        user.setPassword(registerUserDto.getPassword());
        user.setRoles(registerUserDto.getRoles());
        return user;
    }

    public UserDTO toDto(User user){
        return modelMapper.map(user, UserDTO.class);
    }

}
