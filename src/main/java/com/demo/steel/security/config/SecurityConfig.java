package com.demo.steel.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private TokenAuthenticationFilter tokenAuthenticationFilter;
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception{
		builder.userDetailsService(getUserDetailsService());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests().antMatchers("api/**").authenticated().and()
		//.antMatchers("/**").permitAll().and()
		.exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint()).and()
		.addFilterBefore(tokenAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class)
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.csrf().disable();
		
		/*http
		.logout().logoutUrl("/api/auth/logout")
		.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
		.invalidateHttpSession(true)
		.deleteCookies("JSESSIONID");*/
	}

	public TokenAuthenticationFilter tokenAuthenticationFilter(){
		HttpAuthenticationHandler handler = new HttpAuthenticationHandler();
		getTokenAuthenticationFilter().setAuthenticationFailureHandler(handler);
		getTokenAuthenticationFilter().setAuthenticationSuccessHandler(handler);
		return getTokenAuthenticationFilter();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	public TokenAuthenticationFilter getTokenAuthenticationFilter() {
		return tokenAuthenticationFilter;
	}

	public void setTokenAuthenticationFilter(
			TokenAuthenticationFilter tokenAuthenticationFilter) {
		this.tokenAuthenticationFilter = tokenAuthenticationFilter;
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
}
