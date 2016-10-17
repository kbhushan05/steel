package com.demo.steel.security.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import com.demo.steel.security.domain.User;

public class JwtTokenUtil {

    private static final String secret = "hello world";

    public static User parseToken(String token) {
        try {
        	byte[] bytes = DatatypeConverter.parseBase64Binary(token);
        	//byte[] bytes = Base64.getDecoder().decode(token);
        	String actualToken = new String(bytes,"utf-8");
            
        	Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(actualToken)
                    .getBody();

            User u = new User();
            u.setUsername(body.getSubject());
            u.setRole((String) body.get("role"));

            return u;

        } catch (JwtException | ClassCastException | UnsupportedEncodingException e) {
            return null;
        }
    }
    
    public static String generateToken(User u) {
        Claims claims = Jwts.claims().setSubject(u.getUsername());
        claims.put("role", u.getRole());
        claims.setId(u.getUsername());
        claims.setIssuedAt(new Date());
        String token = null;
        
        try {
			String actualToken = Jwts.builder()
			        .setClaims(claims)
			        .signWith(SignatureAlgorithm.HS512, secret)
			        .compact();
			token = DatatypeConverter.printBase64Binary(actualToken.getBytes("utf-8"));
		//	token = Base64.getEncoder().encodeToString(actualToken.getBytes("utf-8"));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        
        return token;
    }
    
}
