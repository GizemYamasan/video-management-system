package com.emlakjet.videostore.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.emlakjet.videostore.repository.entities.BillEntity;

@Repository
public interface BillRepo extends PagingAndSortingRepository<BillEntity, Long> {

	Page<BillEntity> findAllByEmail(String email, PageRequest sortByCreationDate);

}
