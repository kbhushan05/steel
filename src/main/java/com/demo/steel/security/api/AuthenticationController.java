package com.demo.steel.security.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.steel.api.ErrorCode;
import com.demo.steel.api.InvalidInputException;
import com.demo.steel.security.domain.Session;
import com.demo.steel.security.domain.User;
import com.demo.steel.security.service.SessionService;
import com.demo.steel.security.service.UserService;
import com.demo.steel.security.util.JwtTokenUtil;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private SessionService sessionService;
	
	@RequestMapping(method = RequestMethod.POST, value="/login")
	public Session login(@RequestParam String username){
		logger.debug("fetching user for username "+ username);
		User user = userService.get(username);
		user.setPassword("");
		String token = JwtTokenUtil.generateToken(user);
		logger.debug("user token generated for "+ username);
		Session session = getSessionService().getForUser(user);
		logger.debug("retriving existing session for user "+ username);
		if(session != null){
			logger.debug("Session already exists, using same session for user"+ username);
			return session;
		}
		session = getSessionService().create(token, user);
		logger.debug("new session created for "+ username);
		logger.debug("login successful for user "+ username);
		return session;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/logout")
	public void logout(@RequestParam String username){
		logger.debug("logging out user "+ username);
		User user = userService.get(username);
		if(user == null){
			throw new InvalidInputException(ErrorCode.NO_USER_FOUND,"User does not exists");
		}
		user.setPassword("");
		getSessionService().remove(user);
		logger.debug("existing session removed successfully for user "+ username);
		logger.debug("logout successful for user "+ username);
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
