package com.milo.budget.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.milo.budget.exception.SalaryNotFoundException;

@ControllerAdvice(annotations = RestController.class)
public class SalaryNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(SalaryNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String salaryNotFoundHandler(SalaryNotFoundException ex) {
		return ex.getMessage();
	}
}
