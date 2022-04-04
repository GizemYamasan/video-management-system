package com.emlakjet.videostore.repository.entities.mapper;

import org.mapstruct.Mapper;

import com.emlakjet.videostore.domain.Content;
import com.emlakjet.videostore.repository.entities.ContentEntity;

@Mapper
public interface ContentEntityMapper {

	Content entityToDomain(ContentEntity entity);

	ContentEntity domainToEntity(Content domain);
}