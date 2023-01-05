package com.milo.budget.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milo.budget.dto.ExpenseDto;
import com.milo.budget.entity.CasualExpenseEntity;
import com.milo.budget.entity.FixedExpenseEntity;
import com.milo.budget.exception.ExpenseNotFoundException;
import com.milo.budget.exception.UserNotFoundException;
import com.milo.budget.repository.CasualExpenseRepository;
import com.milo.budget.repository.FixedExpenseRepository;
import com.milo.budget.repository.UserRepository;
import com.milo.budget.utils.ObjectMapperUtils;

@Service
public class ExpenseService {

	@Autowired
	private FixedExpenseRepository fixedExpenseRepo;

	@Autowired
	private CasualExpenseRepository casualExpenseRepo;

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserService userService;

	public List<FixedExpenseEntity> getFixedExpensesByUserId(Long userId) {
		return fixedExpenseRepo.findByUserUserId(userId);
	}

	public List<CasualExpenseEntity> getCasualExpensesByUserId(Long userId) {
		return casualExpenseRepo.findByUserUserId(userId);
	}

	public FixedExpenseEntity newFixedExpense(FixedExpenseEntity newExpense, Long userId) {
		newExpense.setUserId(userId);
		FixedExpenseEntity expense = fixedExpenseRepo.save(newExpense);
		userService.updateRemainingBudget(userId, ObjectMapperUtils.map(expense, ExpenseDto.class));
		return expense;
	}

	public void updateFixedExpense(FixedExpenseEntity updatedExpense, Long userId) {
		updatedExpense.setUser(userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId)));
		fixedExpenseRepo.findById(updatedExpense.getExpenseId()).map(expense -> {
			ObjectMapperUtils.map(updatedExpense, expense);
			return fixedExpenseRepo.save(expense);
		}).orElseThrow(() -> new ExpenseNotFoundException(updatedExpense.getExpenseId()));
	}

	public void deleteFixedExpense(Long id) {
		fixedExpenseRepo.deleteById(id);
	}

	public CasualExpenseEntity newCasualExpense(CasualExpenseEntity newExpense, Long userId) {
		newExpense.setUserId(userId);
		CasualExpenseEntity expense = casualExpenseRepo.save(newExpense);
		userService.updateRemainingBudget(userId, ObjectMapperUtils.map(expense, ExpenseDto.class));
		return expense;
	}

	public void updateCasualExpense(CasualExpenseEntity updatedExpense, Long userId) {
		updatedExpense.setUser(userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId)));
		casualExpenseRepo.findById(updatedExpense.getExpenseId()).map(expense -> {
			ObjectMapperUtils.map(updatedExpense, expense);
			return casualExpenseRepo.save(expense);
		}).orElseThrow(() -> new ExpenseNotFoundException(updatedExpense.getExpenseId()));
	}

	public void deleteCasualExpense(Long id) {
		casualExpenseRepo.deleteById(id);
	}
}
