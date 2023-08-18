package com.milo.budget.advice;

import com.milo.budget.exception.SalaryNotFoundException;
import com.milo.budget.exception.SystemBusinessLogicException;
import com.milo.budget.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.milo.budget.exception.ExpenseNotFoundException;

@org.springframework.web.bind.annotation.ControllerAdvice(annotations = RestController.class)
public class ControllerAdvice {

	@ResponseBody
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String userNotFoundHandler(UserNotFoundException ex) {
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(SalaryNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String salaryNotFoundHandler(SalaryNotFoundException ex) {
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(ExpenseNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String expenseNotFoundHandler(ExpenseNotFoundException ex) {
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(SystemBusinessLogicException .class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	String systemBusinessLogicHandler(SystemBusinessLogicException ex) {
		return ex.getMessage();
	}

}
