package com.website.aobongda.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.website.aobongda.dto.UserDTO;
import com.website.aobongda.service.impl.ISignupService;


@RestController
@RequestMapping
public class SignupController {
	@Autowired
	ISignupService signupService;
	
	@PostMapping("/api/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO request, HttpServletRequest siteURL) throws UnsupportedEncodingException, MessagingException{
		return ResponseEntity.ok(signupService.createUser(request, siteURL));
	}
	
	@PostMapping("/api/admin/signup")
	public ResponseEntity<?> registerAdmin(@Valid @RequestBody UserDTO request){
		String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority();
		return ResponseEntity.ok(signupService.createAdmin(request, role));
	}
	
	@GetMapping("/verify")
	public ResponseEntity<?> confimedEmail(@Param("code") String code){
		return ResponseEntity.ok(signupService.enableUser(code));
	}

}
