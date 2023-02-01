package com.milo.budget.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseDto implements Serializable{

	private static final long serialVersionUID = -3264759796188464025L;

	private Long expenseId;

	private String expenseCategory;

	private String expenseType;

	private String description;

	private BigDecimal expenseAmount;

	private Date paymentDate;
}
