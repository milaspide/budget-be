package com.milo.budget.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milo.budget.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
