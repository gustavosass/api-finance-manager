package com.gustavosass.finance.dtos.authenticate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDTO {
	
	private String token;

    private long expiresIn;

    public String getToken() {
        return token;
    }

}
