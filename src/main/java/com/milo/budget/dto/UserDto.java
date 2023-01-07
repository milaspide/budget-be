package com.milo.budget.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto implements Serializable {

	private static final long serialVersionUID = 8126276055814451736L;

	private Long userId;

	private String name;

	private String surname;

	private Date birthDate;

	private String username;

	private String password;

	private String email;

	private BigDecimal bankAccount;

	private BigDecimal fixedSaving;

	private Timestamp createdOn;

	private Timestamp lastLogin;

	private SalaryDto salary;

}
