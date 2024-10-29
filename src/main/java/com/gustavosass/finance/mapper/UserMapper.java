package com.gustavosass.finance.mapper;

import com.gustavosass.finance.dtos.UserDto;
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

    public User toEntity(UserDto userDto){
        return modelMapper.map(userDto, User.class);
    }

    public UserDto toDto(User user){
        return modelMapper.map(user, UserDto.class);
    }

}
