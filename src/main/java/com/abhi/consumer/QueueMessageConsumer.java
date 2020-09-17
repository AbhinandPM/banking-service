package com.abhi.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.abhi.auth.dto.CustomUserDto;
import com.abhi.auth.service.CustomUserDetailsService;
import com.abhi.customer.dto.CustomerDto;
import com.abhi.util.InvalidInputException;

@Component
public class QueueMessageConsumer {

	private final Logger logger = LoggerFactory.getLogger(QueueMessageConsumer.class);

	@Autowired
	private CustomUserDetailsService userService;

	@JmsListener(destination = "${CREATE_CUSTOMER_SUCCESS_QUEUE}")
	public void listener(CustomerDto customer) {
		logger.info("Message received {} ", customer);
		CustomUserDto user = new CustomUserDto();
		user.setUsername(customer.getUsername());
		user.setStatus(2L);
		try {
			userService.updateUserStatus(user);
		} catch (InvalidInputException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
