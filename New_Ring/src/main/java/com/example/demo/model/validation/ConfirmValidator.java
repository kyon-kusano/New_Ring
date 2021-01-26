package com.example.demo.model.validation;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class ConfirmValidator implements ConstraintValidator<Confirm, Object> {

	private String password;
	private String password_Check;
	private String message;

	public void initialize(Confirm constraintAnnotation) {
		password = "password"; // 下記のisValidで使うので、ここでメンバ変数に項目名を入れておいてください。
		password_Check = "password_Check"; // ここも同じ
		message = constraintAnnotation.message(); // Confirmクラスのmessage()です。isValidで使用します。
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);
		Object field1Value = beanWrapper.getPropertyValue(password);
		Object field2Value = beanWrapper.getPropertyValue(password_Check);
		if (Objects.equals(field1Value, field2Value)) {
			return true;
		} else {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message) // このようにmessageの設定を入れないと、エラー内容が出力されません。
					.addConstraintViolation(); // field2の箇所にエラー内容が出力されるようにしています。
			return false;
		}

	}
}