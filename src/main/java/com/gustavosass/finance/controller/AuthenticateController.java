package com.gustavosass.finance.controller;

import com.gustavosass.finance.dtos.LoginUserDTO;
import com.gustavosass.finance.dtos.TokenDTO;
import com.gustavosass.finance.model.User;
import com.gustavosass.finance.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import com.gustavosass.finance.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/authenticate")
@Tag(name = "Authenticate")
public class AuthenticateController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<TokenDTO> authenticate(@RequestBody @Valid LoginUserDTO loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        TokenDTO tokenDto = new TokenDTO();
        tokenDto.setToken(jwtToken);
        tokenDto.setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(tokenDto);
    }
}
