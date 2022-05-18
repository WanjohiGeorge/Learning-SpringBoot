package com.master.demo.io.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.master.demo.io.entities.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
 UserEntity findByEmail(String email);
 UserEntity findByUserID(String userID);
}
