package com.example.demo.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.entity.Employee;
import com.example.demo.service.base.BaseService;

public class UnusedValidator implements ConstraintValidator<Unused, Object> {

	@Autowired
	BaseService baseService;

	private String email;
	private String requestEmail;
	private String message;

	@Override
	public void initialize(Unused constraintAnnotation) {

		this.email = constraintAnnotation.emailProperty();
		this.requestEmail = constraintAnnotation.requestEmailProperty();
		this.message = constraintAnnotation.message();

	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {

		BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);
		String emailString = (String) beanWrapper.getPropertyValue(email);
		String requestEmailString = (String) beanWrapper.getPropertyValue(requestEmail);

		Employee employee = baseService.findByEmail(emailString);
		if (requestEmailString == null) {

			if (employee == null) {
				return true;
			} else {
				context.buildConstraintViolationWithTemplate(message).addPropertyNode(email).addConstraintViolation();
				return false;

			}
		} else {
			if (employee == null || emailString.equals(requestEmailString)) {
				return true;
			} else {
				context.buildConstraintViolationWithTemplate(message).addPropertyNode(email).addConstraintViolation();
				return false;
			}
		}
	}

}
