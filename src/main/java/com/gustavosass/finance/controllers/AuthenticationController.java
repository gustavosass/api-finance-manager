package com.gustavosass.finance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gustavosass.finance.dtos.authenticate.LoginDTO;
import com.gustavosass.finance.dtos.authenticate.TokenDTO;
import com.gustavosass.finance.model.User;
import com.gustavosass.finance.security.JwtService;
import com.gustavosass.finance.service.AuthenticationService;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {
	
	@Autowired
    private final JwtService jwtService;
    
	@Autowired
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<TokenDTO> authenticate(@RequestBody LoginDTO loginDto) {
        User authenticatedUser = authenticationService.authenticate(loginDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        TokenDTO tokenDto = new TokenDTO();

        tokenDto.setToken(jwtToken);
        tokenDto.setExpiresIn(jwtService.getJwtExpiration());

        return ResponseEntity.ok(tokenDto);
    }
}
