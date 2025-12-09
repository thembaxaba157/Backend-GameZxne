package com.game.gamezxne.auth.validator.username;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
public @interface ValidUsername {

    String message() default "Invalid username";
    
    //Don't worry about this for now
    Class<?>[] groups() default {};
    
    //Don't worry about this for now
    Class<? extends Payload>[] payload() default {};
}
