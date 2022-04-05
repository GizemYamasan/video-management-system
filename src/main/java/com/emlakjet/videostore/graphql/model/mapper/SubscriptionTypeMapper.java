package com.emlakjet.videostore.graphql.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.emlakjet.videostore.graphql.model.type.Subscription;

@Mapper(componentModel = "spring")
public abstract class SubscriptionTypeMapper {

	@Mapping(source = "startDate", target = "startDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
	@Mapping(source = "endDate", target = "endDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
	public abstract Subscription toType(com.emlakjet.videostore.domain.Subscription domain);

}
