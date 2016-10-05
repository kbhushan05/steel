package com.demo.steel.security.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.steel.security.domain.Session;
import com.demo.steel.security.domain.User;
import com.demo.steel.security.service.SessionService;
import com.demo.steel.security.service.UserService;
import com.demo.steel.security.util.JwtTokenUtil;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private UserService userService;
	@Autowired
	private SessionService sessionService;
	
	@RequestMapping(method = RequestMethod.POST, value="/login")
	public Session login(@RequestParam String username){
		
		User user = userService.get(username);
		user.setPassword("");
		String token = JwtTokenUtil.generateToken(user);
		Session session = getSessionService().getForUser(user);
		if(session != null){
			return session;
		}
		session = getSessionService().create(token, user);
		return session;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/logout")
	public void logout(@RequestParam String username){
		
		User user = userService.get(username);
		user.setPassword("");
		getSessionService().remove(user);
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public SessionService getSessionService() {
		return sessionService;
	}

	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}
	
}
