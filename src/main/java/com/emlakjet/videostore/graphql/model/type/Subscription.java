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
public class Subscription {

	@GraphQLField
	@GraphQLNonNull
	private String subscriptionType;
	@GraphQLField
	@GraphQLNonNull
	private BigDecimal remainingAmount;
	@GraphQLField
	@GraphQLNonNull
	private String startDate;
	@GraphQLField
	@GraphQLNonNull
	private String endDate;

}
