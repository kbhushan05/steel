package com.demo.steel.security.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class HttpAuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {


	@Override
	public void onAuthenticationSuccess(HttpServletRequest req,
			HttpServletResponse res, Authentication authentication) throws IOException,
			ServletException {
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
	}
	

}
