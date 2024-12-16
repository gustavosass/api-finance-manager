package com.gustavosass.finance.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavosass.finance.exceptions.ExceptionResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		ExceptionResponse exceptionResponse = new ExceptionResponse("Unauthorized access",
				"JWT token is missing or invalid");

		// Configurando a resposta HTTP
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);

		// Serializa ExceptionResponse para JSON
		response.getWriter().write(objectMapper.writeValueAsString(exceptionResponse));
	}

}