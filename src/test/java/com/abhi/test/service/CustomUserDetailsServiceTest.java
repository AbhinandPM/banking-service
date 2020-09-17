package com.abhi.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.abhi.auth.dto.CustomUserDto;
import com.abhi.auth.model.CustomUser;
import com.abhi.auth.repo.UserRepository;
import com.abhi.auth.service.CustomUserDetailsServiceImpl;
import com.abhi.util.CustomBeanUtility;
import com.abhi.util.InvalidInputException;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CustomUserDetailsServiceTest {

	@Mock
	private UserRepository userRepo;

	@Mock
	private JmsTemplate jmsTemplate;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private CustomUserDetailsServiceImpl userDetailsService;

	@Test
	void shouldLoadUserByUsername() {

		CustomUser user = new CustomUser();
		user.setUsername("test");
		user.setRole("test");
		user.setUserId(1L);
		user.setPassword("test");

		when(userRepo.findByUsername(ArgumentMatchers.any())).thenReturn(Optional.of(user));

		UserDetails result = userDetailsService.loadUserByUsername("test");
		assertThat(result).isNotNull();
		assertThat(result.getUsername()).isEqualTo("test");
	}

	@Test
	void shouldThrowExceptionIfUserNotExist() {
		when(userRepo.findByUsername(ArgumentMatchers.any())).thenReturn(Optional.empty());
		assertThrows(UsernameNotFoundException.class, () -> {
			userDetailsService.loadUserByUsername("test");
		});
	}
	
	@Test
	void shouldRegisterAdminUser() throws InvalidInputException {

		CustomUser user = new CustomUser();
		user.setUsername("test");
		user.setRole("test");
		user.setUserId(1L);
		user.setPassword("test");

		when(userRepo.findByUsername(ArgumentMatchers.any())).thenReturn(Optional.empty());
		when(userRepo.save(ArgumentMatchers.any())).thenReturn(user);

		CustomUserDto result = userDetailsService.registerAdminUser(CustomBeanUtility.convertToDto(user));
		assertThat(result).isNotNull();
		assertThat(result.getUsername()).isEqualTo("test");
	}

	@Test
	void shouldRegisterUser() throws InvalidInputException {

		CustomUser user = new CustomUser();
		user.setUsername("test");
		user.setRole("test");
		user.setUserId(1L);
		user.setPassword("test");

		when(userRepo.findByUsername(ArgumentMatchers.any())).thenReturn(Optional.empty());
		when(userRepo.save(ArgumentMatchers.any())).thenReturn(user);

		CustomUserDto result = userDetailsService.registerUser(CustomBeanUtility.convertToDto(user));
		assertThat(result).isNotNull();
		assertThat(result.getUsername()).isEqualTo("test");
	}

	@Test
	void shouldThrowExceptionIfUsernameExistsWhileRegisterUser() throws InvalidInputException {

		CustomUser user = new CustomUser();
		user.setUsername("test");
		user.setRole("test");
		user.setUserId(1L);
		user.setPassword("test");

		when(userRepo.findByUsername(ArgumentMatchers.any())).thenReturn(Optional.of(user));

		assertThrows(InvalidInputException.class,
				() -> userDetailsService.registerUser(CustomBeanUtility.convertToDto(user)));
	}

	@Test
	void shouldThrowExceptionWhileRegisterUserForNull() throws InvalidInputException {
		assertThrows(InvalidInputException.class, () -> userDetailsService.registerUser(null));
	}
	
	@Test
	void shouldUpdateUserStatus() throws InvalidInputException {

		CustomUser user = new CustomUser();
		user.setUsername("test");
		user.setRole("test");
		user.setUserId(1L);
		user.setPassword("test");

		when(userRepo.findByUsername(ArgumentMatchers.any())).thenReturn(Optional.of(user));
		when(userRepo.save(ArgumentMatchers.any())).thenReturn(user);

		CustomUserDto result = userDetailsService.updateUserStatus(CustomBeanUtility.convertToDto(user));
		assertThat(result).isNotNull();
		assertThat(result.getUsername()).isEqualTo("test");
	}
	
	@Test
	void shouldThrowExceptionWhileUpdateUserForNull() throws InvalidInputException {
		assertThrows(InvalidInputException.class, () -> userDetailsService.updateUserStatus(null));
	}

}
