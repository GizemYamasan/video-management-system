package com.emlakjet.videostore.graphql.model.type;

import java.math.BigDecimal;

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
public class Bill {

	@GraphQLField
	@GraphQLNonNull
	private Long billNo;
	@GraphQLField
	@GraphQLNonNull
	private BigDecimal amount;
	@GraphQLField
	@GraphQLNonNull
	private User user;
	@GraphQLField
	@GraphQLNonNull
	private Content content;

}
