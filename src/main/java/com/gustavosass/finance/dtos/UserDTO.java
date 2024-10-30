package com.gustavosass.finance.dtos;

import com.gustavosass.finance.enums.RoleEnum;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private long id;
    private String fullName;
    private String username;
    private Set<RoleEnum> roles;
    private Date createdAt;
    private Date updatedAt;

}
