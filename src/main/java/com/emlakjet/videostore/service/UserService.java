package com.emlakjet.videostore.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emlakjet.videostore.domain.SignUpForm;
import com.emlakjet.videostore.domain.SubscriptionType;
import com.emlakjet.videostore.domain.User;
import com.emlakjet.videostore.exception.EmailAlreadyRegisteredException;
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

	public Optional<User> login(String email, String password) {
		Optional<UserEntity> userEntityOptional = userRepo.findByEmail(email);
		Optional<User> userOptional = userEntityOptional.map(entityMapper::entityToDomain);
		userOptional.ifPresentOrElse(user -> log.debug("user logged in successfully"),
				() -> log.debug("user login failed"));

		return userOptional;
	}

	public User signup(SignUpForm form) {
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
				.password(form.getPassword())
				.currentSubscription(subscription)
				.build();
		//@formatter:on
		UserEntity savedUserEntity = userRepo.save(newUserEntity);
		User user = entityMapper.entityToDomain(savedUserEntity);
		log.debug("user signup successful");
		return user;

	}

	public User save(User user) {
		UserEntity userEntity = entityMapper.domainToEntity(user);
		UserEntity savedUserEntity = userRepo.save(userEntity);
		User savedUser = entityMapper.entityToDomain(savedUserEntity);
		log.debug("user:{} is saved", savedUser.getId());
		return savedUser;
	}

}
