package com.website.aobongda.service.service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.website.aobongda.dto.UserDTO;
import com.website.aobongda.model.User;
import com.website.aobongda.payload.request.ChangePasswordRequest;
import com.website.aobongda.payload.request.ResetPasswordRequest;
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
		String senderName = "Website Aobongda";
		String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
		String mailContent = "<p>Dear " + user.getName() + ",</p>";
		
		mailContent += "<p>Please click the link below to verify your registration</p>";
		mailContent += "<h3><a href=\"" + verifyURL + "\">VERIFY</a></h3>";
		mailContent += "<p>Thank you<br> Website Aobongda</p>";
		
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
	
	@Override
	public DataResponse<?> updateResetPasswordCode(String email, HttpServletRequest siteURL) throws UnsupportedEncodingException, MessagingException {
		
		DataResponse<UserDTO> response = new DataResponse<>();
		User user = repository.findByEmail(email);
		if(user != null) {
			String code = RandomString.make(64);
			user.setVerificationCode(code);
			repository.save(user);
			String resetPasswordLinkString = Utility.getSiteURL(siteURL) +"/reset_password?code="+code;
			sendMailToChangePassword(email, resetPasswordLinkString);
			response.setMessage("We have sent a reset password link to your Email.");
			response.setSuccess(true);
		}else {
			response.setMessage("Could not find any user with email: "+ email);
			response.setSuccess(false);
		}
		
		return response;
		
	}
	
	public void sendMailToChangePassword(String email, String resetPasswordLink) throws UnsupportedEncodingException, MessagingException {
		String subject = "Here's link ";
		String senderName = "Website Book";
		
		String mailContent = "<p>Hello,</p>";
		mailContent += "<p>You have requested to reset your password.</p>";
		mailContent += "<p>Click the link below to change your password:</p>";
		mailContent += "<h3><b><a href=\"" + resetPasswordLink + "\">CHANGE MY PASSWORD</a><b></h3>";
		mailContent += "<p>Ignore this email if you do remember password, or you have not made the request.</p>";
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom("tranduythanhmutd1999@gmail.com", senderName);
		helper.setTo(email);
		helper.setSubject(subject);
		helper.setText(mailContent, true);
		mailSender.send(message);
	}

	@Override
	public DataResponse<?> updatePassword(HttpServletRequest request, ResetPasswordRequest password) {
		DataResponse<?> response = new DataResponse<>();
		
		User user = repository.findByVerifyCode(request.getParameter("code"));
		if(user == null) {
			response.setSuccess(false);
			response.setMessage("Token not found");
		} else {
			if(password.getNewPassword().equals(password.getConfirmPassword())) {
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String encodePassword = passwordEncoder.encode(password.getNewPassword());
				user.setPassword(encodePassword);
				repository.save(user);
				response.setSuccess(true);
				response.setMessage("Password has been updated.");
			}else {
				response.setSuccess(false);
				response.setMessage("Password does not match.");
			}
		}
		
		return  response;
	}

	@Override
	public DataResponse<?> changePassword(String username, ChangePasswordRequest passwordRequest) {
		DataResponse<?> response = new DataResponse<>();
		User user = repository.findByUsername(username).get();
		if(passwordEncoder.matches(passwordRequest.getOldPassword(), user.getPassword())) {
			if(passwordRequest.getNewPassword().equals(passwordRequest.getConfirmNewPassword())) {
				String password = passwordEncoder.encode(passwordRequest.getNewPassword());
				user.setPassword(password);
				repository.save(user);
				response.setSuccess(true);
				response.setMessage("Password has been updated.");
			}else {
				response.setSuccess(false);
				response.setMessage("Password does not match.");
			}
		} else {
			response.setMessage("Incorrect password");
			response.setSuccess(false);
		}
		return response;
	}

	
}
