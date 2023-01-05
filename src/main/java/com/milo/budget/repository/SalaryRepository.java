package com.milo.budget.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.milo.budget.entity.SalaryEntity;

public interface SalaryRepository extends JpaRepository<SalaryEntity, Long> {

}
