package com.emlakjet.videostore.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emlakjet.videostore.repository.entities.UserEntity;

@Repository
public interface UserRepo extends CrudRepository<UserEntity, Long> {

	Optional<UserEntity> findByEmail(String email);

}
