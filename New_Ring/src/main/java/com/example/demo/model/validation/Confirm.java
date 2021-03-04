package com.example.demo.model.validation;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {ConfirmValidator.class})
@Target({TYPE})
@Retention(RUNTIME)
public @interface Confirm {

    String message() default "2つの入力値が異なります";

    @Target({TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        Confirm[] value();
    }
}
