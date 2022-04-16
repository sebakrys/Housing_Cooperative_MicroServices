package com.nsai.spoldzielnia.Validator;

import com.nsai.spoldzielnia.Entity.Building;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class FlatChargesValidator implements Validator{


    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "data", "error.field.required");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "prad", "error.field.required");//zbędne
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gaz", "error.field.required");//zbędne
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "woda_ciepla", "error.field.required");//zbędne
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "woda_zimna", "error.field.required");//zbędne
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scieki", "error.field.required");//zbędne
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ogrzewanie", "error.field.required");//zbędne
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "funduszRemontowy", "error.field.required");//zbędne

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "prad_stawka", "error.field.required");//zbędne
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gaz_stawka", "error.field.required");//zbędne
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "woda_ciepla_stawka", "error.field.required");//zbędne
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "woda_zimna_stawka", "error.field.required");//zbędne
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scieki_stawka", "error.field.required");//zbędne
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ogrzewanie_stawka", "error.field.required");//zbędne
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "funduszRemontowy_stawka", "error.field.required");//zbędne

        if(errors.getErrorCount()==0){

        }else{

            System.out.println("BLEDY Validator flatCharges");
            System.out.println(errors.getAllErrors());
        }
    }
}
