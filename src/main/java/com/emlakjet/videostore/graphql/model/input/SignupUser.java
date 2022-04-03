package com.emlakjet.videostore.graphql.model.input;

import graphql.annotations.annotationTypes.GraphQLConstructor;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @GraphQLConstructor)
public class SignupUser {

	@GraphQLField
	@GraphQLNonNull
	private String email;
	@GraphQLField
	@GraphQLNonNull
	private String firstName;
	@GraphQLField
	@GraphQLNonNull
	private String lastName;
	@GraphQLField
	@GraphQLNonNull
	private String password;
}
