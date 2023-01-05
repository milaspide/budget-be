package com.milo.budget.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.milo.budget.exception.ExpenseNotFoundException;

@ControllerAdvice(annotations = RestController.class)
public class ExpenseNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(ExpenseNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String expenseNotFoundHandler(ExpenseNotFoundException ex) {
		return ex.getMessage();
	}
}
