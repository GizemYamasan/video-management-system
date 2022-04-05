package com.emlakjet.videostore.graphql.resolvers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.emlakjet.videostore.domain.User;
import com.emlakjet.videostore.graphql.model.mapper.BillTypeMapper;
import com.emlakjet.videostore.graphql.model.mapper.ContentTypeMapper;
import com.emlakjet.videostore.graphql.model.type.Bill;
import com.emlakjet.videostore.graphql.model.type.Content;
import com.emlakjet.videostore.service.ContentService;
import com.emlakjet.videostore.service.PaymentService;
import com.emlakjet.videostore.service.UserService;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.kickstart.annotations.GraphQLQueryResolver;
import graphql.kickstart.spring.error.ErrorContext;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@GraphQLQueryResolver
public class Queries implements ApplicationContextAware {

	private static ContentService contentService;
	private static PaymentService paymentService;
	private static UserService userService;

	private static BillTypeMapper billTypeMapper;
	private static ContentTypeMapper contentTypeMapper;

	@GraphQLField
	@GraphQLNonNull
	@GraphQLDescription("test query with hello world!")
	public static String hello(final @Nullable String who) {
		String name = Optional.ofNullable(who).orElse("world");
		return "hello " + name + "!";
	}

	@GraphQLField
	@GraphQLNonNull
	@GraphQLDescription("get bills of a user")
	public static List<Bill> getBills(String token, int page, int size) {
		Objects.requireNonNull(token, "token is required");
		User user = userService.findByToken(token);
		var bills = paymentService.getBills(user, page, size);
		return billTypeMapper.toType(bills);
	}

	@GraphQLField
	@GraphQLNonNull
	@GraphQLDescription("get available contents")
	public static List<Content> getContents(int page, int size) {
		var contents = contentService.getContents(page, size);
		return contentTypeMapper.toType(contents);
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		contentService = applicationContext.getBean(ContentService.class);
		paymentService = applicationContext.getBean(PaymentService.class);
		userService = applicationContext.getBean(UserService.class);
		billTypeMapper = applicationContext.getBean(BillTypeMapper.class);
		contentTypeMapper = applicationContext.getBean(ContentTypeMapper.class);
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
