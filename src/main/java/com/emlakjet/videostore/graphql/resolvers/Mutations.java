package com.emlakjet.videostore.graphql.resolvers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.emlakjet.videostore.domain.SubscriptionType;
import com.emlakjet.videostore.graphql.model.input.SaveContent;
import com.emlakjet.videostore.graphql.model.input.SignupUser;
import com.emlakjet.videostore.graphql.model.mapper.BillTypeMapper;
import com.emlakjet.videostore.graphql.model.mapper.ContentTypeMapper;
import com.emlakjet.videostore.graphql.model.mapper.SignupTypeMapper;
import com.emlakjet.videostore.graphql.model.mapper.SubscriptionTypeMapper;
import com.emlakjet.videostore.graphql.model.mapper.UserTypeMapper;
import com.emlakjet.videostore.graphql.model.type.Bill;
import com.emlakjet.videostore.graphql.model.type.Content;
import com.emlakjet.videostore.graphql.model.type.Subscription;
import com.emlakjet.videostore.graphql.model.type.Token;
import com.emlakjet.videostore.graphql.model.type.User;
import com.emlakjet.videostore.service.ContentService;
import com.emlakjet.videostore.service.PaymentService;
import com.emlakjet.videostore.service.UserService;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.kickstart.annotations.GraphQLMutationResolver;
import graphql.kickstart.spring.error.ErrorContext;
import lombok.NoArgsConstructor;

/**
 * Same restrictions / considerations apply as for the query resolver.
 *
 * @see Queries
 */
@Service
@NoArgsConstructor
@GraphQLMutationResolver
public class Mutations implements ApplicationContextAware {

	private static ContentService contentService;
	private static PaymentService paymentService;
	private static UserService userService;

	private static BillTypeMapper billTypeMapper;
	private static ContentTypeMapper contentTypeMapper;
	private static UserTypeMapper userTypeMapper;
	private static SignupTypeMapper signupTypeMapper;
	private static SubscriptionTypeMapper subscriptionTypeMapper;

	@GraphQLField
	@GraphQLNonNull
	@GraphQLDescription("Creates a new user.")
	public static User signupUser(final @GraphQLNonNull SignupUser input) {
		var signupUser = signupTypeMapper.toDomain(input);
		var user = userService.signup(signupUser);
		User userType = userTypeMapper.toType(user);
		userType.getCurrentSubscription()
				.setSubscriptionType(user.getCurrentSubscription().getSubscriptionType().name());
		return userType;
	}

	@GraphQLField
	@GraphQLNonNull
	@GraphQLDescription("user login.")
	public static Token loginUser(@GraphQLNonNull String email, @GraphQLNonNull String password) {
		String token = userService.login(email, password);
		return new Token(token);
	}

	@GraphQLField
	@GraphQLNonNull
	@GraphQLDescription("Creates a new content.")
	public static Content createContent(final @GraphQLNonNull SaveContent input) {
		var content = contentTypeMapper.toDomain(input);
		var savedContent = contentService.save(content);
		Content contentType = contentTypeMapper.toType(savedContent);
		return contentType;
	}

	@GraphQLField
	@GraphQLNonNull
	@GraphQLDescription("user can purchase a content.")
	public static Bill purchaseContent(@GraphQLNonNull String token, @GraphQLNonNull Long contentId) {
		var user = userService.findByToken(token);
		var bill = paymentService.purchaseContent(user, contentId);
		Bill billType = billTypeMapper.toType(bill);
		return billType;
	}

	@GraphQLField
	@GraphQLNonNull
	@GraphQLDescription("user can purchase a subscription.")
	public static Subscription purchaseSubscription(@GraphQLNonNull String token,
			@GraphQLNonNull String subscriptionType) {
		var user = userService.findByToken(token);
		var subscription = paymentService.purchaseSubscription(user, SubscriptionType.valueOf(subscriptionType));
		Subscription subscriptionTypeReturn = subscriptionTypeMapper.toType(subscription);
		return subscriptionTypeReturn;
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		contentService = applicationContext.getBean(ContentService.class);
		paymentService = applicationContext.getBean(PaymentService.class);
		userService = applicationContext.getBean(UserService.class);
		billTypeMapper = applicationContext.getBean(BillTypeMapper.class);
		contentTypeMapper = applicationContext.getBean(ContentTypeMapper.class);
		userTypeMapper = applicationContext.getBean(UserTypeMapper.class);
		signupTypeMapper = applicationContext.getBean(SignupTypeMapper.class);
		subscriptionTypeMapper = applicationContext.getBean(SubscriptionTypeMapper.class);
	}

	@ExceptionHandler(value = Exception.class)
	public GraphQLError toCustomError(Exception e, ErrorContext errorContext) {
		//@formatter:off
	    Map<String, Object> extensions = Optional.ofNullable(errorContext.getExtensions()).orElseGet(HashMap::new);
//	    extensions.put("my-custom-code", "some-custom-error");
	    return GraphqlErrorBuilder.newError()
	        .message(e.getMessage())
	        .extensions(extensions)
	        .errorType(errorContext.getErrorType())
	        .locations(errorContext.getLocations())
	        .path(errorContext.getPath())
	        .build();
	    //@formatter:on
	}
}
