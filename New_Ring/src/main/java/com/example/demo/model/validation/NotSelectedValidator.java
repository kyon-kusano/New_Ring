package com.example.demo.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotSelectedValidator implements ConstraintValidator<NotSelected, Integer> {

	public void initialize(NotSelected constraintAnnotation) {
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {

		if (value != 1) {
			return true;
		}
		return false;
	}
}