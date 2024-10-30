package com.gustavosass.finance.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDTO {
    private String token;
    private long expiresIn;
}
