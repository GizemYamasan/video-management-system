package com.emlakjet.videostore.graphql.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.emlakjet.videostore.graphql.model.type.User;

@Mapper(componentModel = "spring")
public interface UserTypeMapper {

	User toType(com.emlakjet.videostore.domain.User domain);

	List<User> toType(List<com.emlakjet.videostore.domain.User> domain);

	com.emlakjet.videostore.domain.User toDomain(User type);
}
