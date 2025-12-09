package com.game.gamezxne.auth.validator.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    //check if there's atleast 1 lowercase,1 uppercase, 1 number and special character
   final String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value==null || value.isBlank()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password is required").addConstraintViolation();
            return false;
        }

        else if(value.length()<6 || value.length()>18){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password must be 6-18 characters").addConstraintViolation();

            return false;
        }
        else if(value.contains(" ")){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password should not contain Space character").addConstraintViolation();
            return false;
        }

        else if(!value.matches(passwordRegex)){
            context.disableDefaultConstraintViolation();
            
            context.buildConstraintViolationWithTemplate("Password should contain at least one lowercase letter, at least one uppercase letter, at least one number at least one special character")
                                                        .addConstraintViolation();
            return false;
        }

        return true;

    }
    
}
