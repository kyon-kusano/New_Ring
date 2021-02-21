package com.example.demo.model.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

@Documented
@Constraint(validatedBy = { UnusedValidator.class })
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface Unused {

	String message() default "すでに登録済みのメールアドレスです。";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String emailProperty();

	String requestEmailProperty();

	@Target({ TYPE, ANNOTATION_TYPE })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		Unused[] value();
	}
}
