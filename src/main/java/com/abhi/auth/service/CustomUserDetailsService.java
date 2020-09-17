package com.abhi.auth.service;

import com.abhi.auth.dto.CustomUserDto;
import com.abhi.util.InvalidInputException;

public interface CustomUserDetailsService {

	CustomUserDto registerUser(CustomUserDto customUserDto) throws InvalidInputException;

	CustomUserDto registerAdminUser(CustomUserDto customUserDto) throws InvalidInputException;

	CustomUserDto updateUserStatus(CustomUserDto customUserDto) throws InvalidInputException;

}
