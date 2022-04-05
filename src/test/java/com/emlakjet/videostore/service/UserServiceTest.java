package com.emlakjet.videostore.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.emlakjet.videostore.domain.SignupUser;
import com.emlakjet.videostore.domain.Subscription;
import com.emlakjet.videostore.domain.SubscriptionType;
import com.emlakjet.videostore.domain.User;
import com.emlakjet.videostore.repository.UserRepo;
import com.emlakjet.videostore.repository.entities.UserEntity;
import com.emlakjet.videostore.repository.entities.mapper.UserEntityMapper;

public class UserServiceTest {

	private UserService userService;
	private UserRepo userRepo;
	private UserEntityMapper entityMapper;
	private PasswordEncoder passwordEncoder;

	@BeforeEach
	public void setup() {
		entityMapper = Mappers.getMapper(UserEntityMapper.class);
		userRepo = Mockito.mock(UserRepo.class);
		passwordEncoder = Mockito.mock(PasswordEncoder.class);
		userService = new UserService(userRepo, entityMapper, passwordEncoder);
	}

	@Test
	public void testLoginSuccess() {
		long id = 5L;
		String pwd = "abc";
		String email = "joe@example.com";
		//@formatter:off
		UserEntity userEntity = UserEntity.builder()
				.id(id)
				.email(email)
				.password(pwd)
				.build();
		//@formatter:on		
		when(userRepo.findByEmail(email)).thenReturn(Optional.of(userEntity));
		when(userRepo.save(any())).thenReturn(null);
		when(passwordEncoder.matches(any(), any())).thenReturn(true);

		String token = userService.login(email, pwd);
		Assertions.assertNotNull(token);
	}

	@Test
	public void testSignupSuccess() {
		long id = 15L;
		String pwd = "abc";
		String email = "joe@example.com";
		String firstName = "joe";
		String lastName = "doe";

		//@formatter:off
		SignupUser form = SignupUser.builder()
				.email(email)
				.firstName(firstName)
				.lastName(lastName)
				.password(pwd)
				.build();
		//@formatter:on		
		when(userRepo.findByEmail(email)).thenReturn(Optional.empty());
		when(userRepo.save(any())).thenAnswer(inv -> {
			UserEntity entity = inv.getArgument(0);
			entity.setId(id);
			return entity;
		});

		User user = userService.signup(form);
		Assertions.assertNotNull(user);
		Assertions.assertEquals(id, user.getId());
		Assertions.assertEquals(email, user.getEmail());
	}

	@Test
	public void testSaveSuccess() {
		long id = 25L;
		String email = "joe@example.com";
		String firstName = "joe";
		String lastName = "doe";
		LocalDateTime now = LocalDateTime.now();
		//@formatter:off
		User user = User.builder()
				.id(id)
				.firstName(firstName)
				.lastName(lastName)
				.email(email)
				.currentSubscription(Subscription.builder()
						.endDate(now.plusDays(25))
						.startDate(now.minusDays(5))
						.remainingAmount(BigDecimal.TEN)
						.subscriptionType(SubscriptionType.GOLD)
						.build())
				.build();
		UserEntity userEntity = entityMapper.domainToEntity(user);
		//@formatter:on
		when(userRepo.findById(userEntity.getId())).thenReturn(Optional.of(userEntity));
		when(userRepo.save(any())).thenAnswer(inv -> {
			UserEntity entity = inv.getArgument(0);
			return entity;
		});

		User savedUser = userService.save(user);
		Assertions.assertNotNull(savedUser);
		Assertions.assertEquals(id, savedUser.getId());
		Assertions.assertEquals(email, savedUser.getEmail());
	}
}
