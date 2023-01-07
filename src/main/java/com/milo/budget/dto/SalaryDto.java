package com.milo.budget.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaryDto implements Serializable {

	private static final long serialVersionUID = -2386983263454379324L;

	private Long id;

	private BigDecimal netSalary;

	private BigDecimal grossSalary;

	private Integer paymentDay;
}
