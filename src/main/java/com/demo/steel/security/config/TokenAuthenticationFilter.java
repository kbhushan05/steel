package com.demo.steel.security.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import com.demo.steel.security.domain.User;
import com.demo.steel.security.service.SessionService;
import com.demo.steel.security.util.JsonUtil;
import com.demo.steel.security.util.JwtTokenUtil;
import com.demo.steel.util.StringUtil;

@Component
public class TokenAuthenticationFilter extends
		AbstractAuthenticationProcessingFilter {

	@Autowired
	private SessionService sessionService;
	
	public TokenAuthenticationFilter() {
		super("/api/**");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req,
			HttpServletResponse rep) throws AuthenticationException,
			IOException, ServletException {

		String url = req.getRequestURL().toString();
		if (url.endsWith("/login")
				&& req.getMethod().equalsIgnoreCase(HttpMethod.POST.toString())) {
			byte[] buffer = new byte[1024];
			InputStream in = req.getInputStream();
			System.out.println("Hello world " + in.available());
			if(req.getContentLength() < 0){
				throw new AuthenticationCredentialsNotFoundException(
						"Authentication credentials not found.");
			}
			StringBuffer strBfr = new StringBuffer();
			while (in.read(buffer) > 0) {
				String s = new String(buffer);
				strBfr.append(s);
			}
			in.close();
			User user = JsonUtil.getObject(strBfr.toString(), User.class);
			if(user == null || StringUtil.isEmpty(user.getUsername())){
				throw new AuthenticationCredentialsNotFoundException(
						"Authentication credentials not found.");
			}
			UsernamePasswordAuthenticationToken preAuth = new UsernamePasswordAuthenticationToken(
					user.getUsername(), user.getPassword());
			return getAuthenticationManager().authenticate(preAuth);
		}
			String header = req.getHeader("Authorization");
			if (header == null || !header.startsWith("Bearer ")) {
				throw new AuthenticationCredentialsNotFoundException(
						"Authentication credentials not found.");
			}

			String authToken = header.substring(7);
			boolean isActive = getSessionService().isActive(authToken);
			if(isActive){
				User user = JwtTokenUtil.parseToken(authToken);
				GrantedAuthority role = new SimpleGrantedAuthority(user.getRole());
				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				authorities.add(role);
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
						user.getUsername(), user.getPassword(), authorities);
				return token;
			}
			throw new SessionAuthenticationException("Session is not active.");
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}


	@Autowired
	@Override
	public void setAuthenticationManager(
			AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}

	public SessionService getSessionService() {
		return sessionService;
	}

	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}
	
}
