package com.demo.steel.security.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.demo.steel.security.domain.User;

@Component
public class DaoUserDetailsService implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(DaoUserDetailsService.class);
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		logger.debug("loading a user by username "+ username);
		User appUser = userService.get(username);
		logger.debug("found user: "+ appUser);
		if(appUser == null){
			return new org.springframework.security.core.userdetails.User("","",new ArrayList<GrantedAuthority>());
		}
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
				appUser.getRole());
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(grantedAuthority);
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(
				appUser.getUsername(), appUser.getPassword(), authorities);
		logger.debug("created UserDetails.");
		return userDetails;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
