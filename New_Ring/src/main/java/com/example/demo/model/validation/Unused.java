package com.example.demo.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

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
