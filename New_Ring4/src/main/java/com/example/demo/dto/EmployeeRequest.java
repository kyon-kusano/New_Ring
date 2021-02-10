package com.example.demo.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * ユーザー情報 リクエストデータ
 */
@Data
public class EmployeeRequest implements Serializable {

	private String username;

	private String email;

	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	private String sex;

	private int department;

	private String address;

	private String telephone_Number;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date join_Date;

	private boolean authority;

}