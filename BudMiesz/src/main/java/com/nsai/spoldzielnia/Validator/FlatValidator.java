package com.nsai.spoldzielnia.Validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class FlatValidator implements Validator{


    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        //todo flat Validator ValidationUtils.rejectIfEmptyOrWhitespace(errors, "buildingNumber", "error.field.required");
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "street", "error.field.required");
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postalCode", "error.field.required");
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "error.field.required");

        if(errors.getErrorCount()==0){

        }else{

            System.out.println("BLEDY Validator Flat");
            System.out.println(errors.getAllErrors());
        }
    }
}
