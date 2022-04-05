package com.emlakjet.videostore.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.emlakjet.videostore.domain.SignupUser;
import com.emlakjet.videostore.domain.SubscriptionType;
import com.emlakjet.videostore.domain.User;
import com.emlakjet.videostore.exception.EmailAlreadyRegisteredException;
import com.emlakjet.videostore.exception.InvalidCredentialsException;
import com.emlakjet.videostore.repository.UserRepo;
import com.emlakjet.videostore.repository.entities.SubscriptionEntity;
import com.emlakjet.videostore.repository.entities.SubscriptionTypeEntity;
import com.emlakjet.videostore.repository.entities.UserEntity;
import com.emlakjet.videostore.repository.entities.mapper.UserEntityMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserService {

	private final UserRepo userRepo;
	private final UserEntityMapper entityMapper;
	private final PasswordEncoder passwordEncoder;

	public String login(String email, String password) {
		return userRepo.findByEmail(email).filter(u -> passwordEncoder.matches(password, u.getPassword())).map(u -> {
			log.debug("user login successfully");
			u.setToken(UUID.randomUUID().toString());
			userRepo.save(u);
			return u;
		}).map(UserEntity::getToken).orElseThrow(() -> {
			log.debug("user login failed");
			return new InvalidCredentialsException();
		});
	}

	public User findByToken(String token) {
		return userRepo.findByToken(token).map(entityMapper::entityToDomain).orElseThrow(() -> {
			log.debug("invalid token");
			return new InvalidCredentialsException();
		});
	}

	public User signup(SignupUser form) {
		userRepo.findByEmail(form.getEmail()).ifPresent(userEntity -> {
			throw new EmailAlreadyRegisteredException();
		});
		//@formatter:off
		SubscriptionEntity subscription = SubscriptionEntity.builder()
				.subscriptionType(SubscriptionTypeEntity.NO_SUBSCRIPTION)
				.remainingAmount(SubscriptionType.NO_SUBSCRIPTION.getPackagePrice())
				.endDate(LocalDateTime.MAX)
				.startDate(LocalDateTime.now())
				.build();
		UserEntity newUserEntity = UserEntity.builder()
				.email(form.getEmail())
				.firstName(form.getFirstName())
				.lastName(form.getLastName())
				.password(passwordEncoder.encode(form.getPassword()))
				.currentSubscription(subscription)
				.build();
		//@formatter:on
		UserEntity savedUserEntity = userRepo.save(newUserEntity);
		User user = entityMapper.entityToDomain(savedUserEntity);
		log.debug("user signup successful");
		return user;
	}

	public User save(User user) {
		UserEntity foundUserEntity = userRepo.findById(user.getId()).orElseThrow();
		UserEntity userEntity = entityMapper.domainToEntity(user);
		foundUserEntity.setCurrentSubscription(userEntity.getCurrentSubscription());
		UserEntity savedUserEntity = userRepo.save(foundUserEntity);
		User savedUser = entityMapper.entityToDomain(savedUserEntity);
		log.debug("user:{} is saved", savedUser.getId());
		return savedUser;
	}

}
