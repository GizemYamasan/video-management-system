package com.emlakjet.videostore.graphql.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.emlakjet.videostore.graphql.model.input.SaveContent;
import com.emlakjet.videostore.graphql.model.type.Content;

@Mapper(componentModel = "spring")
public interface ContentTypeMapper {

	Content toType(com.emlakjet.videostore.domain.Content domain);

	List<Content> toType(List<com.emlakjet.videostore.domain.Content> domain);

	com.emlakjet.videostore.domain.Content toDomain(Content type);

	com.emlakjet.videostore.domain.Content toDomain(SaveContent type);
}
