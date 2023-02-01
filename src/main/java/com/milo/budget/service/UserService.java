package com.milo.budget.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.milo.budget.dto.ExpenseDto;
import com.milo.budget.entity.CasualExpenseEntity;
import com.milo.budget.entity.FixedExpenseEntity;
import com.milo.budget.entity.UserEntity;
import com.milo.budget.exception.SalaryNotFoundException;
import com.milo.budget.exception.UserNotFoundException;
import com.milo.budget.repository.CasualExpenseRepository;
import com.milo.budget.repository.FixedExpenseRepository;
import com.milo.budget.repository.SalaryRepository;
import com.milo.budget.repository.UserRepository;
import com.milo.budget.utils.ObjectMapperUtils;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private SalaryRepository salaryRepo;

	@Autowired
	private FixedExpenseRepository fixedExpenseRepo;

	@Autowired
	private CasualExpenseRepository casualExpenseRepo;

	public UserEntity getUserById(Long id) {
		return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	public UserEntity newUser(UserEntity newUser) {
		newUser.getSalary().setUser(newUser);
		return userRepo.save(newUser);
	}

	public void updateUser(UserEntity newUser) {
		userRepo.findById(newUser.getUserId()).map(user -> {
			newUser.setPassword(user.getPassword());
			ObjectMapperUtils.map(newUser, user);
			return userRepo.save(user);
		});
	}

	public void deleteUser(Long id) {
		userRepo.deleteById(id);
	}

	public Long getRemainingDays(Long id) {
		LocalDate localNow = LocalDate.now();
		Integer year = localNow.getYear();
		Integer month = localNow.getMonthValue();
		Integer day = salaryRepo.findById(id).orElseThrow(() -> new SalaryNotFoundException(id)).getPaymentDay();
		Calendar cal = Calendar.getInstance();
		//Month value is 0-based. e.g., 0 for January
		cal.set(year, month - 1, day);
		Date paymentDate = cal.getTime();
		Date now = Date.from(localNow.atStartOfDay(ZoneId.systemDefault()).toInstant());
		long diffInMillies = paymentDate.getTime() - now.getTime();
		return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

	public void calculateRemainingBudget(Long id) {
		AtomicReference<BigDecimal> currentFixedAmount = new AtomicReference<>(new BigDecimal(0));
		List<FixedExpenseEntity> fixedExpenses = fixedExpenseRepo.findByUserUserId(id);
		fixedExpenses.forEach(expense -> {
			BigDecimal oldFixedAmount = currentFixedAmount.get();
			if (currentFixedAmount.compareAndSet(oldFixedAmount, oldFixedAmount.add(expense.getExpenseAmount())))
				return;
		});
		AtomicReference<BigDecimal> currentCasualAmount = new AtomicReference<>(new BigDecimal(0));
		List<CasualExpenseEntity> casualExpenses = casualExpenseRepo.findByUserUserId(id);
		casualExpenses.forEach(expense -> {
			BigDecimal oldCasualAmount = currentCasualAmount.get();
			if (currentCasualAmount.compareAndSet(oldCasualAmount, oldCasualAmount.add(expense.getExpenseAmount())))
				return;
		});
		UserEntity user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		BigDecimal netSalary = salaryRepo.findById(id).orElseThrow(() -> new SalaryNotFoundException(id)).getNetSalary();
		BigDecimal remainingBudget = netSalary.subtract(currentFixedAmount.get()).subtract(currentCasualAmount.get());
		user.setRemainingBudget(remainingBudget);
		userRepo.save(user);
	}

	public void updateRemainingBudget(Long id, ExpenseDto expense) {
		UserEntity user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		user.setRemainingBudget(user.getRemainingBudget().subtract(expense.getExpenseAmount()));
		userRepo.save(user);
	}
}
