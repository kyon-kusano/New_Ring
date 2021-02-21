package com.example.demo.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.model.entity.Address;

import lombok.Data;

/**
 * ユーザー情報 リクエストデータ
 */
@Data
public class EmployeeRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotBlank(message="名前を入力してください")
	private String username;
	
	@Email
	@NotBlank(message="アドレスを入力してください")
	private String email;

	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="選択してください")
	private Date birthday;
	
	@NotBlank(message="選択してください")
	private String sex;

//    private Department department;
	private int department;
	
	private Address address;
	
	@NotBlank(message="電話番号を入力してください")
	private String telephone_Number;
	
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="選択してください")
	private Date join_Date;

	private boolean authority;

}