package com.milo.budget.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.milo.budget.dto.ExpenseDto;
import com.milo.budget.entity.CasualExpenseEntity;
import com.milo.budget.entity.FixedExpenseEntity;
import com.milo.budget.service.ExpenseService;
import com.milo.budget.utils.ObjectMapperUtils;

@RestController
@RequestMapping(path = "/expenses")
public class ExpenseController {

	@Autowired
	ExpenseService expenseService;

	@GetMapping(value = "fixed/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<ExpenseDto>> getFixedExpensesByUserId(@PathVariable Long userId) {
		return ResponseEntity
				.ok(ObjectMapperUtils.mapAll(expenseService.getFixedExpensesByUserId(userId), ExpenseDto.class));
	}

	@GetMapping(value = "casual/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<List<ExpenseDto>> getCasualExpensesByUserId(@PathVariable Long userId) {
		return ResponseEntity
				.ok(ObjectMapperUtils.mapAll(expenseService.getCasualExpensesByUserId(userId), ExpenseDto.class));
	}

	@PostMapping(value = "fixed/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<ExpenseDto> newFixedExpense(@PathVariable Long userId, @RequestBody ExpenseDto newExpenseDto) {
		FixedExpenseEntity newExpense = ObjectMapperUtils.map(newExpenseDto, FixedExpenseEntity.class);
		return ResponseEntity
				.ok(ObjectMapperUtils.map(expenseService.newFixedExpense(newExpense, userId), ExpenseDto.class));
	}

	@PutMapping(value = "fixed/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	void updateFixedExpense(@PathVariable Long userId, @RequestBody ExpenseDto expenseDto) {
		FixedExpenseEntity newExpense = ObjectMapperUtils.map(expenseDto, FixedExpenseEntity.class);
		expenseService.updateFixedExpense(newExpense, userId);
	}
	
	@DeleteMapping(value = "fixed/{id}")
	@ResponseStatus(HttpStatus.OK)
	void deleteFixedExpense(@PathVariable Long id) {
		expenseService.deleteFixedExpense(id);
	}

	@PostMapping(value = "casual/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<ExpenseDto> newCasualExpense(@PathVariable Long userId, @RequestBody ExpenseDto newExpenseDto) {
		CasualExpenseEntity newExpense = ObjectMapperUtils.map(newExpenseDto, CasualExpenseEntity.class);
		return ResponseEntity
				.ok(ObjectMapperUtils.map(expenseService.newCasualExpense(newExpense, userId), ExpenseDto.class));
	}

	@PutMapping(value = "casual/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	void updateCasualExpense(@PathVariable Long userId, @RequestBody ExpenseDto expenseDto) {
		CasualExpenseEntity newExpense = ObjectMapperUtils.map(expenseDto, CasualExpenseEntity.class);
		expenseService.updateCasualExpense(newExpense, userId);
	}
	
	@DeleteMapping(value = "casual/{id}")
	@ResponseStatus(HttpStatus.OK)
	void deleteCasualExpense(@PathVariable Long id) {
		expenseService.deleteCasualExpense(id);
	}
}
