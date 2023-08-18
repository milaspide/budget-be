package com.milo.budget.service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.milo.budget.exception.SystemBusinessLogicException;
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

	public List<ExpenseDto> getExpenses(Long userId, String type, Long month) {
		Calendar cal = Calendar.getInstance();
		LocalDate localNow = LocalDate.now();
		Date startOfMonth;
		Date endOfMonth;
		if (month == null) {
			//Month value is 0-based. e.g., 0 for January
			cal.set(localNow.getYear(), localNow.getMonthValue() - 1, 1);
			startOfMonth = cal.getTime();
			cal.set(localNow.getYear(), localNow.getMonthValue(), 1);
			endOfMonth = cal.getTime();
		} else {
			cal.set(localNow.getYear(), month.intValue() - 1, 1);
			startOfMonth = cal.getTime();
			cal.set(localNow.getYear(), month.intValue(), 1);
			endOfMonth = cal.getTime();
		}
		if (type != null && type.equals("CASUAL")) {
			List<CasualExpenseEntity> expenses =
					casualExpenseRepo.findAllByUserIdAndPaymentDateBetween(userId, startOfMonth, endOfMonth);
			return ObjectMapperUtils.mapAll(expenses, ExpenseDto.class);
		} else if (type != null && type.equals("FIXED")) {
			List<FixedExpenseEntity> expenses =
					fixedExpenseRepo.findAllByUserIdAndPaymentDateBetween(userId, startOfMonth, endOfMonth);
			return ObjectMapperUtils.mapAll(expenses, ExpenseDto.class);
		} else {
			throw new SystemBusinessLogicException("Parameter \"type\" must not be \"null\"");
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
			expense.setUserId(userId);
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
