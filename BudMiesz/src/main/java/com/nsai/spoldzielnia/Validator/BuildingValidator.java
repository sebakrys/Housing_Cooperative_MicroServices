package com.nsai.spoldzielnia.Validator;

import com.nsai.spoldzielnia.Entity.Building;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class BuildingValidator implements Validator{


    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "buildingNumber", "error.field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "street", "error.field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "postalCode", "error.field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "error.field.required");

        if(!((Building)target).getPostalCode().matches("^([0-9]{2}-[0-9]{3})$")){//validacja regex postal code
            System.out.println(((Building)target).getPostalCode());
            errors.rejectValue("postalCode", "error.incorrect.postal");
        }


        if(errors.getErrorCount()==0){

        }else{

            System.out.println("BLEDY Validator Building");
            System.out.println(errors.getAllErrors());
        }
    }
}
