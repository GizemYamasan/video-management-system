package com.emlakjet.videostore.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.emlakjet.videostore.repository.entities.ContentEntity;

@Repository
public interface ContentRepo extends PagingAndSortingRepository<ContentEntity, Long> {

}
