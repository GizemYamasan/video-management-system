package com.emlakjet.videostore.graphql.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.emlakjet.videostore.graphql.model.input.SignupUser;

@Mapper(componentModel = "spring")
public interface SignupTypeMapper {

	SignupUser toType(com.emlakjet.videostore.domain.SignupUser domain);

	List<SignupUser> toType(List<com.emlakjet.videostore.domain.SignupUser> domain);

	com.emlakjet.videostore.domain.SignupUser toDomain(SignupUser type);
}
