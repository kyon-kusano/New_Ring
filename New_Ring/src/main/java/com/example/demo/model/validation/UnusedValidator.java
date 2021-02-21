package com.example.demo.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.model.entity.Employee;
import com.example.demo.service.base.BaseService;

public class UnusedValidator implements ConstraintValidator<Unused, String> {

	@Autowired
	BaseService baseService;

	public void initialize(Unused constraintAnnotation) {
	}

	public boolean isValid(String value, ConstraintValidatorContext context) {

		Employee employee = baseService.findByEmail(value);

		Employee loggedEmployee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String loggedEmployeeEmail = loggedEmployee.getEmail();

		if (employee == null || loggedEmployeeEmail.equals(value)) {
			return true;
		}
		return false;
	}
}