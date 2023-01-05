package com.milo.budget.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "salaries", schema = "budget")
public class SalaryEntity implements Serializable{

	@Id
	@Column(name = "user_id")
	private Long id;

	@Column(name = "net_salary")
	private BigDecimal netSalary;

	@Column(name = "gross_salary")
	private BigDecimal grossSalary;
	
	@Column(name = "payment_day")
	private Integer paymentDay;
	
}
