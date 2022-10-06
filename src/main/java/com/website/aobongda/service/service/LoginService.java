package com.website.aobongda.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.website.aobongda.payload.request.LoginRequest;
import com.website.aobongda.payload.response.JwtResponse;
import com.website.aobongda.security.jwt.JwtProvider;
import com.website.aobongda.security.userprincipal.UserPrincipal;



@Service
public class LoginService {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtProvider jwtProvider;
	
	public JwtResponse authenticateUser(LoginRequest request, String rolePrefix) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(rolePrefix + request.getUsername(), request.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtProvider.createToken(authentication, rolePrefix);
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		
		return new JwtResponse(token, userPrincipal.getUsername(), userPrincipal.getAuthorities());
	}

}
