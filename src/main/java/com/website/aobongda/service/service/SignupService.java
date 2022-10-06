package com.website.aobongda.service.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.website.aobongda.dto.UserDTO;
import com.website.aobongda.model.User;
import com.website.aobongda.payload.response.DataResponse;
import com.website.aobongda.repository.UserRepository;
import com.website.aobongda.service.impl.ISignupService;
import com.website.aobongda.utility.datatype.ERole;
import com.website.aobongda.utility.datatype.Utility;

import net.bytebuddy.utility.RandomString;

@Service
@Component
public class SignupService implements ISignupService{
	@Autowired
	UserService userService;
	@Autowired
	UserRepository repository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	JavaMailSender mailSender;
	@Override
	public DataResponse<UserDTO> createAdmin(UserDTO request, String role) {
		DataResponse<UserDTO> response = new DataResponse<>();
		if(request.getUsername() != null && userService.existsByUsername(request.getUsername())) {
			response.setMessage("The username is existed");
			response.setSuccess(false);
			return response;
		}
		if(request.getEmail() != null && userService.existsByEmail(request.getEmail())) {
			response.setMessage("The email is existed");
			response.setSuccess(false);
			return response;
		}
		if(request.getPhone() != null && userService.existsByPhone(request.getPhone())) {
			response.setMessage("The phone number is existed");
			response.setSuccess(false);
			return response;
		}
		User user = new User(request.getName(), request.getUsername(), passwordEncoder.encode(request.getPassword()), request.getPhone(), request.getEmail());
		
		user.setRoles(ERole.ADMIN);
		userService.save(user);
		response.setMessage("Register success");
		response.setSuccess(true);
		response.setData(modelMapper.map(user, UserDTO.class));
		return response;
	}
	
	@Override
	public DataResponse<UserDTO> createUser(UserDTO request, HttpServletRequest siteURL) throws UnsupportedEncodingException, MessagingException {
		DataResponse<UserDTO> response = new DataResponse<>();
		if(request.getUsername() != null && userService.existsByUsername(request.getUsername())) {
			response.setMessage("The username is existed");
			response.setSuccess(false);
			return response;
		}
		if(request.getEmail() != null && userService.existsByEmail(request.getEmail())) {
			response.setMessage("The email is existed");
			response.setSuccess(false);
			return response;
		}
		if(request.getPhone() != null && userService.existsByPhone(request.getPhone())) {
			response.setMessage("The phone number is existed");
			response.setSuccess(false);
			return response;
		}
		
		String randomCode = RandomString.make(64);
		
		User user = new User(request.getName(), request.getUsername(), passwordEncoder.encode(request.getPassword()), request.getPhone(), request.getEmail());
		user.setEnabled(false);
		user.setStatus(true);
		user.setVerificationCode(randomCode);
		
		user.setRoles(ERole.USER);
		userService.save(user);
		response.setMessage("Register success");
		response.setSuccess(true);
		response.setData(modelMapper.map(user, UserDTO.class));
		sendVerificationEmail(response.getData(), Utility.getSiteURL(siteURL));
		return response;
	}
	
	public void sendVerificationEmail(UserDTO user, String siteURL) throws UnsupportedEncodingException, MessagingException {
		String subject = "Please verify your registration";
		String senderName = "Website Book";
		String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
		String mailContent = "<p>Dear " + user.getName() + ",</p>";
		
		mailContent += "<p>Please click the link below to verify your registration</p>";
		mailContent += "<h3><a href=\"" + verifyURL + "\">VERIFY</a></h3>";
		mailContent += "<p>Thank you<br> Website Book</p>";
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom("tranduythanhmutd1999@gmail.com", senderName);
		helper.setTo(user.getEmail());
		helper.setSubject(subject);
		helper.setText(mailContent, true);
		mailSender.send(message);
	}

	@Override
	public DataResponse<?> enableUser(String verify) {
		DataResponse<?> response = new DataResponse<>();
		User user = repository.findByVerifyCode(verify);
		if(user == null || user.isEnabled()) {
			response.setSuccess(false);
			response.setMessage("User does not exist or is confirmed");
		}
		else {
			repository.enable(user.getId());
			repository.save(user);
			response.setSuccess(true);
			response.setMessage("Thank you for your email confirmation");
		}
		return response;
	}


}
