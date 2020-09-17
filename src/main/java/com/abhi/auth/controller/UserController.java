package com.abhi.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abhi.auth.dto.CustomUserDto;
import com.abhi.auth.service.CustomUserDetailsService;
import com.abhi.util.InvalidInputException;

@RestController
public class UserController {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@PostMapping("/user/register")
	public CustomUserDto registerUser(@RequestBody CustomUserDto customUserDto) throws InvalidInputException {
		customUserDto = customUserDetailsService.registerUser(customUserDto);
		return customUserDto;
	}
	
	@PostMapping("/adminuser/register")
	public CustomUserDto registerAdminUser(@RequestBody CustomUserDto customUserDto) throws InvalidInputException {
		customUserDto = customUserDetailsService.registerAdminUser(customUserDto);
		return customUserDto;
	}
}
