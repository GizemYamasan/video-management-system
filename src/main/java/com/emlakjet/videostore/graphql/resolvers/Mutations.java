package com.emlakjet.videostore.graphql.resolvers;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.emlakjet.videostore.graphql.model.input.SignupUser;
import com.emlakjet.videostore.graphql.model.type.User;
import com.emlakjet.videostore.service.ContentService;
import com.emlakjet.videostore.service.PaymentService;
import com.emlakjet.videostore.service.UserService;

import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.kickstart.annotations.GraphQLMutationResolver;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Same restrictions / considerations apply as for the query resolver.
 *
 * @see Queries
 */
@Service
@NoArgsConstructor
@GraphQLMutationResolver
@Slf4j
public class Mutations implements ApplicationContextAware {

	private static ContentService contentService;
	private static PaymentService paymentService;
	private static UserService userService;

	@GraphQLField
	@GraphQLNonNull
	@GraphQLDescription("Creates a new person.")
	public static User signupUser(final @GraphQLNonNull SignupUser signupUser) {
		log.info("signup user input: {}", signupUser);

		return User.builder().email(signupUser.getEmail()).build();
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		contentService = applicationContext.getBean(ContentService.class);
		paymentService = applicationContext.getBean(PaymentService.class);
		userService = applicationContext.getBean(UserService.class);
	}
}
