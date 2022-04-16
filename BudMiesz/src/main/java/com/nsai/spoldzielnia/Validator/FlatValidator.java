package com.nsai.spoldzielnia.Validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class FlatValidator implements Validator{


    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "flatNumber", "error.field.required");

        if(errors.getErrorCount()==0){

        }else{

            System.out.println("BLEDY Validator Flat");
            System.out.println(errors.getAllErrors());
        }
    }
}
