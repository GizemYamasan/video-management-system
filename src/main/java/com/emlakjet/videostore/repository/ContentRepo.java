package com.emlakjet.videostore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emlakjet.videostore.repository.entities.ContentEntity;

@Repository
public interface ContentRepo extends CrudRepository<ContentEntity, Long> {

}
