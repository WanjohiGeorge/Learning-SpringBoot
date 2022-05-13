package com.master.demo.io.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.master.demo.io.entities.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
 UserEntity findByEmail(String email);
 UserEntity findByUserID(String userID);
}
