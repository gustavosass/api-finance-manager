package com.gustavosass.finance.dtos;

import com.gustavosass.finance.model.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long id;
    private String fullName;
    private String username;
    private Date createdAt;
    private Date updatedAt;

}
