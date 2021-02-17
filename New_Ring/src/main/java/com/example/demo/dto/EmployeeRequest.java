package com.example.demo.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.model.entity.Address;

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

//    private Department department;
	private int department;

	private Address address;

	private String telephone_Number;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date join_Date;

	private boolean authority;

}