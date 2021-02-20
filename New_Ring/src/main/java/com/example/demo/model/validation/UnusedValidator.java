package com.example.demo.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.entity.Employee;
import com.example.demo.service.base.BaseService;

public class UnusedValidator implements ConstraintValidator<Unused, String> {

	@Autowired
	BaseService baseService;

	public void initialize(Unused constraintAnnotation) {
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {

		Employee employee = baseService.findByEmail(value);
		if (employee == null) {
			return true;
		}
		return false;
	}
}