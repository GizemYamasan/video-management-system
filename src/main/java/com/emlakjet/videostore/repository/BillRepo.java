package com.emlakjet.videostore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emlakjet.videostore.repository.entities.BillEntity;

@Repository
public interface BillRepo extends CrudRepository<BillEntity, Long> {

}
