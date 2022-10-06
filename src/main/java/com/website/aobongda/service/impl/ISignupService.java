package com.website.aobongda.service.impl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import com.website.aobongda.dto.UserDTO;
import com.website.aobongda.payload.response.DataResponse;



public interface ISignupService {
	DataResponse<UserDTO> createAdmin(UserDTO request, String role);
	void sendVerificationEmail(UserDTO user, String siteURL) throws UnsupportedEncodingException, MessagingException;
	DataResponse<UserDTO> createUser(UserDTO request, HttpServletRequest siteURL)
			throws UnsupportedEncodingException, MessagingException;
	
	DataResponse<?> enableUser(String verify);
}
