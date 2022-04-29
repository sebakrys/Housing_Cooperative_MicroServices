package com.example.mieszkancy.Controller;


import com.example.mieszkancy.Entity.Person;
import com.example.mieszkancy.Repository.PersonRepository;
import com.example.mieszkancy.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PersonController {

// TODO DODAJ UZUN MODYFIKUJ DODAJ VALIDATOR

    private PersonService personService;
    private PersonRepository personRepository;

    @Autowired
    private PersonController(PersonService personService, PersonRepository personRepository){
        this.personService = personService;
        this.personRepository = personRepository;
    }

    //POST
    @PostMapping(value = "/addPerson")
    public ResponseEntity<Person> addNewPerson(@RequestBody Person person, BindingResult bindingResult){
        System.out.println(person.getFirstName() +  " " + person.getLastName()+ " " + person.getLogin());

        if (person.getTelephone().equals("")){
            System.out.println("Dodawanie osoby");
            return ResponseEntity.ok(personService.addPerson(person));
        }

        System.out.println("Problem z dodaniem osoby");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }


    @GetMapping(value = "/getEmail/{id}")
    public ResponseEntity<String> getUserIdByEmail(@PathVariable Long id){
        if(id > 0){
            System.out.println("Zwracam uzytkownika email z przeslanego id");
            return ResponseEntity.ok(personRepository.findById(id).get().getEmail());
        }
        else {
            System.out.println("Wartość niezgodna z baza danych. Sprawdz czy przesyłany jest dobry id");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }



    //PUT
    @PutMapping("/updatePerson")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person, BindingResult bindingResult){

        if(personService.getPerson(person.getId())==null)
        {
            System.out.println("Id jest puste");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

        }

        System.out.println("edit persony");
        return ResponseEntity.ok(personService.editPerson(person));

    }

    @GetMapping("/getPersons")
    public List<Person> getAllPersons(){ return personService.listPersons();}

    @GetMapping("/getPerson/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id){
        return personRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    //DELETE

    @DeleteMapping("/deletePerson/{personId}")
    public ResponseEntity<Person> deletePerson(@PathVariable Long personId){
        System.out.println(" Usuwanie osoby");

        if(personService.getPerson(personId)==null)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

        personService.removePerson(personId);
        return ResponseEntity.ok().build();
    }

}
