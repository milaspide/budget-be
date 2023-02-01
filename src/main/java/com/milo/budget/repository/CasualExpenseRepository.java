package com.milo.budget.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milo.budget.entity.CasualExpenseEntity;

public interface CasualExpenseRepository extends JpaRepository<CasualExpenseEntity, Long> {

	List<CasualExpenseEntity> findByUserUserId(Long userId);
	
	List<CasualExpenseEntity> findAllByUserIdAndPaymentDateAfter (Long userId, Date startOfMonth);

}
