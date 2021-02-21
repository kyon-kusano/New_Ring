package com.example.demo.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.demo.model.validation.Confirm;

import lombok.Data;

/**
 * ユーザー情報 リクエストデータ
 */

@Confirm
@Data
public class PasswordRequest implements Serializable {

	@NotEmpty(message = "パスワードを入力してください。")
	@Size(min = 8, max = 16, message = "パスワードは{min}文字以上{max}文字以下です。")
	@Pattern(regexp = "[a-zA-Z0-9]*")
	private String password;

	
	private String password_Check;

}