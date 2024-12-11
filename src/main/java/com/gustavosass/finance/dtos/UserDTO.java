package com.gustavosass.finance.dtos;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private long id;
    private String fullName;
    private String username;
    private Date createdAt;
    private Date updatedAt;

}
