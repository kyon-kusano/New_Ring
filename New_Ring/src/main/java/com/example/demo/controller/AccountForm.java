package com.example.demo.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.controller.Employee.Authority;

import lombok.Data;

@Data
public class AccountForm implements Serializable {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private long id;

	private String username;

	private String password;

	private String email;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	private String sex;


	private Long department;

	private String telephone_Number;

	private String address;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date join_Date;

	private Set<Authority> authorities;
}
