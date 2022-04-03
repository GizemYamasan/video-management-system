package com.emlakjet.videostore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emlakjet.videostore.repository.entities.UserEntity;

@Repository
public interface UserRepo extends CrudRepository<UserEntity, Long> {

}
