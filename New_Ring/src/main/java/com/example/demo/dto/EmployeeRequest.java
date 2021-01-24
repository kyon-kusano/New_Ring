package com.example.demo.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * ユーザー情報 リクエストデータ
 */
@Data
public class EmployeeRequest implements Serializable {

	@NotEmpty
	private String username;

	@Email
	@NotEmpty
	private String email;

	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	@NotEmpty
	private String sex;

	@NotEmpty
	private String department;

	@NotEmpty
	private String address;

	@NotEmpty
	private String telephone_Number;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date join_Date;

}