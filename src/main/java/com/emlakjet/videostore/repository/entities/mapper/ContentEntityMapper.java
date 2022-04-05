package com.emlakjet.videostore.repository.entities.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.emlakjet.videostore.domain.Content;
import com.emlakjet.videostore.repository.entities.ContentEntity;

@Mapper(componentModel = "spring")
public interface ContentEntityMapper {

	Content entityToDomain(ContentEntity entity);

	ContentEntity domainToEntity(Content domain);

	List<Content> entityToDomain(List<ContentEntity> entities);

	List<ContentEntity> domainToEntity(List<Content> domains);
}