package com.emlakjet.videostore.repository.entities.mapper;

import org.mapstruct.Mapper;

import com.emlakjet.videostore.domain.User;
import com.emlakjet.videostore.repository.entities.UserEntity;

@Mapper
public interface UserEntityMapper {

	User entityToDomain(UserEntity entity);

	UserEntity domainToEntity(User domain);
}