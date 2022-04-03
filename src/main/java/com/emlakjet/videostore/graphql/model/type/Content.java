package com.emlakjet.videostore.graphql.model.type;

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
public class Content {

	@GraphQLField
	@GraphQLNonNull
	private String name;
	@GraphQLField
	@GraphQLNonNull
	private ContentType type;

}
