package com.milo.budget.api;

import java.util.List;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.milo.budget.dto.ExpenseDto;
import com.milo.budget.entity.CasualExpenseEntity;
import com.milo.budget.entity.FixedExpenseEntity;
import com.milo.budget.service.ExpenseService;
import com.milo.budget.utils.ObjectMapperUtils;

@Log4j2
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "/expenses")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @GetMapping(value = "fixed/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ExpenseDto>> getFixedExpensesByUserId(@PathVariable Long userId) {
        return ResponseEntity
                .ok(ObjectMapperUtils.mapAll(expenseService.getFixedExpensesByUserId(userId), ExpenseDto.class));
    }

    @GetMapping(value = "fixed/month/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ExpenseDto>> getFixedExpensesByUserIdAndMonth(@PathVariable Long userId) {
        return ResponseEntity.ok(
                ObjectMapperUtils.mapAll(expenseService.getFixedExpensesByUserIdAndMonth(userId), ExpenseDto.class));
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

    @GetMapping(value = "casual/month/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ExpenseDto>> getCasualExpensesByUserIdAndMonth(@PathVariable Long userId,
                                                                       @RequestParam(required = false) Long month) {
        return ResponseEntity.ok(ObjectMapperUtils
                .mapAll(expenseService.getCasualExpensesByUserIdAndMonth(userId, month), ExpenseDto.class));
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
