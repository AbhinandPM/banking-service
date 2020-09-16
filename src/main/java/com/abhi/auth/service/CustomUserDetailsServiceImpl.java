package com.abhi.auth.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.abhi.auth.dto.CustomUserDto;
import com.abhi.auth.model.CustomUser;
import com.abhi.auth.model.CustomUserDetails;
import com.abhi.auth.repo.UserRepository;
import com.abhi.customer.dto.CustomerDto;
import com.abhi.util.CustomBeanUtility;
import com.abhi.util.InvalidInputException;

@Service
@Transactional
public class CustomUserDetailsServiceImpl implements UserDetailsService, CustomerUserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${CREATE_USER_QUEUE}")
	private String createUserQueue;

	@Value("${adminRole}")
	private String adminRole;

	@Value("${userRole}")
	private String userRole;

	@Override
	public UserDetails loadUserByUsername(String username) {

		Optional<CustomUser> user = userRepo.findByUsername(username);
		if (user.isPresent()) {
			return new CustomUserDetails(user.get());
		} else {
			throw new UsernameNotFoundException("Not found: " + username);
		}
	}

	@Override
	public CustomUserDto registerUser(CustomUserDto customUserDto) throws InvalidInputException {
		this.saveUser(customUserDto, userRole);
		CustomerDto customer = new CustomerDto();
		BeanUtils.copyProperties(customUserDto, customer, "id");
		jmsTemplate.convertAndSend(this.createUserQueue, customer);
		customUserDto.setPassword("*****");
		return customUserDto;
	}

	@Override
	public CustomUserDto registerAdminUser(CustomUserDto customUserDto) throws InvalidInputException {
		return this.saveUser(customUserDto, adminRole);
	}

	private CustomUserDto saveUser(CustomUserDto customUserDto, String role) throws InvalidInputException {
		if (null == customUserDto) {
			throw new InvalidInputException("User details are required.");
		}
		Optional<CustomUser> userOp = userRepo.findByUsername(customUserDto.getUsername());
		if (userOp.isPresent()) {
			throw new InvalidInputException("Username already exists");
		}
		customUserDto.setStatus(1L);
		customUserDto.setCreatedDate(LocalDateTime.now());
		customUserDto.setRole(role);
		CustomUser user = CustomBeanUtility.convertToDomain(customUserDto);
		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
		userRepo.save(user);
		customUserDto.setUserId(user.getUserId());
		customUserDto.setPassword("*****");
		return customUserDto;
	}

	@Override
	public CustomUserDto updateUserStatus(CustomUserDto customUserDto) throws InvalidInputException {
		if (null == customUserDto) {
			throw new InvalidInputException("User details are required.");
		}
		Optional<CustomUser> userOp = userRepo.findByUsername(customUserDto.getUsername());
		if (userOp.isPresent()) {
			CustomUser user = userOp.get();
			user.setStatus(customUserDto.getStatus());
			userRepo.save(user);
		}

		return customUserDto;
	}

}
