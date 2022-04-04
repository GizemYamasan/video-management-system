package com.emlakjet.videostore.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.emlakjet.videostore.domain.Content;
import com.emlakjet.videostore.repository.ContentRepo;
import com.emlakjet.videostore.repository.entities.ContentEntity;
import com.emlakjet.videostore.repository.entities.mapper.ContentEntityMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ContentService {

	private final ContentRepo repo;
	private final ContentEntityMapper contentEntityMapper;

	public Content save(Content content) {
		ContentEntity contentEntity = contentEntityMapper.domainToEntity(content);
		ContentEntity savedContentEntity = repo.save(contentEntity);
		return contentEntityMapper.entityToDomain(savedContentEntity);
	}

	public List<Content> getContents(int page, int size) {
		PageRequest sortByName = PageRequest.of(page, size, Sort.by("name"));
		Page<ContentEntity> contentEntities = repo.findAll(sortByName);
		return contentEntities.stream().map(contentEntityMapper::entityToDomain).collect(Collectors.toList());
	}

	public Optional<Content> getContent(long contentId) {
		// TODO Auto-generated method stub
		return null;
	}
}
