package com.milo.budget.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milo.budget.entity.FixedExpenseEntity;

public interface FixedExpenseRepository extends JpaRepository<FixedExpenseEntity, Long> {

	List<FixedExpenseEntity> findByUserUserId(Long userId);
}
