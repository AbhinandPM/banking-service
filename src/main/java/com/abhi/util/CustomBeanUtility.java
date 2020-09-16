package com.abhi.util;

import org.springframework.beans.BeanUtils;

import com.abhi.auth.dto.CustomUserDto;
import com.abhi.auth.model.CustomUser;

public class CustomBeanUtility {

	private CustomBeanUtility() {
	}

	public static CustomUser convertToDomain(CustomUserDto userDto) {
		if (null == userDto) {
			return null;
		}
		CustomUser customUser = new CustomUser();
		BeanUtils.copyProperties(userDto, customUser);
		return customUser;
	}

	public static CustomUserDto convertToDto(CustomUser customUser) {
		if (null == customUser) {
			return null;
		}
		CustomUserDto userDto = new CustomUserDto();
		BeanUtils.copyProperties(customUser, userDto);
		return userDto;
	}

}
