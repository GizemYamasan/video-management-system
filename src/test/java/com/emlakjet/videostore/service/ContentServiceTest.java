package com.emlakjet.videostore.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.emlakjet.videostore.domain.Content;
import com.emlakjet.videostore.domain.ContentType;
import com.emlakjet.videostore.repository.ContentRepo;
import com.emlakjet.videostore.repository.entities.ContentEntity;
import com.emlakjet.videostore.repository.entities.ContentTypeEntity;
import com.emlakjet.videostore.repository.entities.mapper.ContentEntityMapper;

public class ContentServiceTest {

	private ContentService contentService;
	private ContentRepo contentRepo;
	private ContentEntityMapper contentEntityMapper;

	@BeforeEach
	public void setup() {
		contentEntityMapper = Mappers.getMapper(ContentEntityMapper.class);
		contentRepo = Mockito.mock(ContentRepo.class);
		contentService = new ContentService(contentRepo, contentEntityMapper);
	}

	@Test
	public void testSaveSuccess() {
		//@formatter:off
		Content content = Content.builder()
				.name("The Adam Project")
				.type(ContentType.SCIENCE_FICTION)
				.build();
		long id = 14L;
		ContentEntity contentEntity = ContentEntity.builder()
				.id(id)
				.name("The Adam Project")
				.type(ContentTypeEntity.SCIENCE_FICTION)
				.build();
		//@formatter:on
		when(contentRepo.save(any())).thenReturn(contentEntity);

		Content saved = contentService.save(content);
		Assertions.assertNotNull(saved);
		Assertions.assertEquals(id, saved.getId());

		verify(contentRepo, times(1)).save(any());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetAllSuccess() {
		//@formatter:off
		ContentEntity contentEntity1 = ContentEntity.builder()
				.id(10L)
				.name("The Adam Project")
				.type(ContentTypeEntity.SCIENCE_FICTION)
				.build();
		ContentEntity contentEntity2 = ContentEntity.builder()
				.id(11L)
				.name("Z The Ant")
				.type(ContentTypeEntity.ANIMATION)
				.build();
		//@formatter:on
		Page<ContentEntity> page = Mockito.mock(Page.class);
		when(page.getContent()).thenReturn(List.of(contentEntity1, contentEntity2));
		when(contentRepo.findAll(any(PageRequest.class))).thenReturn(page);

		List<Content> contents = contentService.getContents(0, 2);
		Assertions.assertNotNull(contents);
		Assertions.assertEquals(2, contents.size());
		Assertions.assertEquals(10, contents.get(0).getId());
		Assertions.assertEquals(11, contents.get(1).getId());

		verify(contentRepo, times(1)).findAll(any(PageRequest.class));
	}
}
