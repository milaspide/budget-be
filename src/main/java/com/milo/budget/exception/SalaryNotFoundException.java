package com.milo.budget.exception;

public class SalaryNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SalaryNotFoundException(Long id) {
		super("Could not find salary " + id);
	}

	public SalaryNotFoundException() {
		super();
	}

}
