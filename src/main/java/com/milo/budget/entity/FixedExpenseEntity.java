package com.milo.budget.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "fixed_expenses", schema = "budget")
public class FixedExpenseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="expense_id")
	private Long expenseId;
	
	@Column(name="user_id")
	private Long userId;
	
	@Column(name = "expense_category")
	private String expenseCategory;

	@Column(name = "expense_type")
	private String expenseType;

	@Column(name = "description")
	private String description;

	@Column(name = "expense_amount")
	private BigDecimal expenseAmount;

	@Column(name = "payment_date")
	private Date paymentDate;
	
	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	@JsonIgnore
	private UserEntity user;
	
}
