package com.emlakjet.videostore.graphql.resolvers;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.emlakjet.videostore.service.ContentService;
import com.emlakjet.videostore.service.PaymentService;
import com.emlakjet.videostore.service.UserService;

import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.kickstart.annotations.GraphQLQueryResolver;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@GraphQLQueryResolver
public class Queries implements ApplicationContextAware {

	private static ContentService contentService;
	private static PaymentService paymentService;
	private static UserService userService;

	@GraphQLField
	@GraphQLNonNull
	@GraphQLDescription("Say hello to anybody, or the whole world!")
	public static String hello(final @Nullable String who) {
		Objects.requireNonNull(contentService, "Must only be called after spring context is initialized.");
		return "Hello " + Optional.ofNullable(who).orElse("World");
	}

//	@GraphQLField
//	@GraphQLNonNull
//	@GraphQLDescription("Returns all people in the database.")
//	public static List<Person> people() {
//		return personRepository.findAll();
//	}
//
//	@GraphQLField
//	@GraphQLNonNull
//	@GraphQLDescription("Returns pets")
//	public static List<Pet> pets() {
//		return List.of(new Dog(), new Cat());
//	}
//
//	@GraphQLField
//	@GraphQLNonNull
//	@GraphQLDescription("Returns vehicles")
//	public static List<Vehicle> vehicles() {
//		return List.of(new Car("ABC-123", 4), new Truck("CBA-321", 12));
//	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		contentService = applicationContext.getBean(ContentService.class);
		paymentService = applicationContext.getBean(PaymentService.class);
		userService = applicationContext.getBean(UserService.class);
	}
}
