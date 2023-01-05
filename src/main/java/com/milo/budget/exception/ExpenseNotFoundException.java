package com.milo.budget.exception;

public class ExpenseNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExpenseNotFoundException(Long id) {
		super("Could not find expense " + id);
	}

	public ExpenseNotFoundException() {
		super();
	}
}
