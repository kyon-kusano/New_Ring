package com.example.demo.model.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = { ConfirmValidator.class })
@Target({ TYPE })
@Retention(RUNTIME)
public @interface Confirm {

	String message() default "2つの入力値が異なります";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	@Target({ TYPE })
	@Retention(RUNTIME)
	@Documented
	public @interface List {
		Confirm[] value();
	}
}