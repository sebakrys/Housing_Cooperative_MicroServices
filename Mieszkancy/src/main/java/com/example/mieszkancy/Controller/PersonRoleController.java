package com.example.mieszkancy.Controller;


import com.example.mieszkancy.Entity.PersonRole;
import com.example.mieszkancy.Service.PersonRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

public class PersonRoleController {
    //TODO DODAJ VALIDATOR

    private PersonRoleService personRoleService;

    @Autowired
    public void PersonRoleController(PersonRoleService personRoleService){
        this.personRoleService = personRoleService;
    }

    //POST
    @PostMapping(value = "/addPersonRole")
    public ResponseEntity addPersonRole(@RequestBody PersonRole personRole){

        if(personRole.getRole().equals(""))
        {
            System.out.println("Dodawanie roli");
            return ResponseEntity.ok(personRoleService.addPersonRole(personRole));
        }
        else {
            System.out.println("Problem z dodaniem roli");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }
}
