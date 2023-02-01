package com.milo.budget.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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
	
	public List<FixedExpenseEntity> getFixedExpensesByUserIdAndMonth(Long userId) {
		LocalDate localNow = LocalDate.now();
//		Date now = Date.from(localNow.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Calendar cal = Calendar.getInstance();
		//Month value is 0-based. e.g., 0 for January
		cal.set(localNow.getYear(), localNow.getMonthValue() - 1, 1);
		Date startOfMonth = cal.getTime();
		return fixedExpenseRepo.findAllByPaymentDateAfter(startOfMonth);
	}

	public List<CasualExpenseEntity> getCasualExpensesByUserId(Long userId) {
		return casualExpenseRepo.findByUserUserId(userId);
	}
	
	public List<CasualExpenseEntity> getCasualExpensesByUserIdAndMonth(Long userId, Long month) {
		LocalDate localNow = LocalDate.now();
		Calendar cal = Calendar.getInstance();
		if(month == null) {
			//Month value is 0-based. e.g., 0 for January
			cal.set(localNow.getYear(), localNow.getMonthValue() - 1, 1);
			Date startOfMonth = cal.getTime();
			return casualExpenseRepo.findAllByUserIdAndPaymentDateAfter(userId, startOfMonth);
		} else {
			cal.set(localNow.getYear(), month.intValue() - 1, 1);
			Date startOfMonth = cal.getTime();
			return casualExpenseRepo.findAllByUserIdAndPaymentDateAfter(userId, startOfMonth);
		}
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
