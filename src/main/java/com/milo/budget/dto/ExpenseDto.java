package com.milo.budget.dto;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseDto {

	private Long expenseId;

	private String expenseCategory;

	private String expenseType;

	private String description;

	private BigDecimal expenseAmount;

	private Date paymentDate;
}
