package com.game.gamezxne.auth.validator.username;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        
        if(value.isBlank() || value==null){
            //remove the default message from ValidUsername class
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Username is Required").addConstraintViolation();
            return false;
        }

        else if(value.length()< 3 || value.length()>30){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Username must be 3-30 characters").addConstraintViolation();
            return false;
        }

            return true;
        


        
    }

}
