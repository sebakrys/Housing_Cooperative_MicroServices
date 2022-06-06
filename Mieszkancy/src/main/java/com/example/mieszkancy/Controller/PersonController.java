package com.example.mieszkancy.Controller;


import com.example.mieszkancy.Entity.KeycloakDataJson;
import com.example.mieszkancy.Entity.Person;
import com.example.mieszkancy.Repository.PersonRepository;
import com.example.mieszkancy.Service.AuthService;
import com.example.mieszkancy.Service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.security.Principal;
import java.util.List;
import java.util.stream.Stream;

@CrossOrigin(origins = "http://localhost:3000")//for React
@RestController
@RequiredArgsConstructor
@Slf4j
public class PersonController {

// TODO DODAJ UZUN MODYFIKUJ DODAJ VALIDATOR

    @Autowired
    private AuthService authService;

    private PersonService personService;
    private PersonRepository personRepository;

    @Autowired
    private PersonController(PersonService personService, PersonRepository personRepository){
        this.personService = personService;
        this.personRepository = personRepository;
    }

    //POST
    @PostMapping(value = "/addPerson",produces = "application/json")
    public ResponseEntity<Person> addNewPerson(@RequestBody Person person, @RequestHeader (name="Authorization") String token){

        boolean admin = authService.isAdmin(token);
        if(!admin) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("enabled", person.isEnabled());
        personJsonObject.put("username", person.getLogin());
        personJsonObject.put("email", person.getEmail());
        personJsonObject.put("firstName", person.getFirstName());
        personJsonObject.put("lastName", person.getLastName());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);

        try {
            ResponseEntity<String> re = new RestTemplate().exchange("http://localhost:8080/admin/realms/resourceServer/users",
                    HttpMethod.POST, request, String.class);
            if(re.getStatusCodeValue() == 201){//jesli nie 201 wycofaj
                return ResponseEntity.ok(personService.addPerson(person));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        }catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

        }

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

    @GetMapping(value = "/getNames/{id}")
    public ResponseEntity<String> getUserNamesById(@PathVariable Long id){
        if(id > 0){
            System.out.println("Zwracam uzytkownika email z przeslanego id");
            return ResponseEntity.ok(personRepository.findById(id).get().getFirstName()+" "+personRepository.findById(id).get().getLastName());
        }
        else {
            System.out.println("Wartość niezgodna z baza danych. Sprawdz czy przesyłany jest dobry id");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    @GetMapping(value = "/getID/{login}")
    public ResponseEntity<Long> getIdByLogin(@PathVariable String login){
        if(login != null){
            System.out.println("Zwracam uzytkownika id z przeslanego loginu");
            return ResponseEntity.ok(personRepository.findByLogin(login).getId());
        }
        else {
            System.out.println("Wartość niezgodna z baza danych. Sprawdz czy przesyłany jest dobry login");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    //PUT
    @PutMapping(value = "/updatePerson/{id}", produces = "application/json")
    public ResponseEntity updatePerson(@RequestBody Person person,
                                               @PathVariable("id") String id,
                                               @RequestHeader (name="Authorization") String token){

        boolean admin = authService.isAdmin(token);
        if(admin)
        {
            JSONObject personJsonObject = new JSONObject();
            personJsonObject.put("enabled", person.isEnabled());
            personJsonObject.put("username", person.getLogin());
            personJsonObject.put("email", person.getEmail());
            personJsonObject.put("firstName", person.getFirstName());
            personJsonObject.put("lastName", person.getLastName());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", token);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);
            ResponseEntity<KeycloakDataJson> re = new RestTemplate().exchange("http://localhost:8080/admin/realms/resourceServer/users/" + id,
                    HttpMethod.GET, request, KeycloakDataJson.class);
//            KeycloakDataJson tmp2 = re.getBody();

            Person tmp = personRepository.findByLogin(re.getBody().getUsername());
            if((re.getBody().getUsername().equals(tmp.getLogin())))
            {

                SetVariablesInPerson(tmp, person);

                ResponseEntity<String> update = new RestTemplate().exchange("http://localhost:8080/admin/realms/resourceServer/users/" + id,
                        HttpMethod.PUT, request, String.class);

                personService.editPerson(tmp);
            }
            else
            {
                System.out.println("Jestes admin, ale nie modyfikujesz dwoch innych uzytkownikow?");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        return ResponseEntity.ok().build();
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

    @DeleteMapping("/deletePerson/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable("id") String id,
                                               @RequestHeader (name="Authorization") String token){
        boolean admin = authService.isAdmin(token);
        if(admin)
        {

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", token);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<String>(headers);
            ResponseEntity<KeycloakDataJson> re = new RestTemplate().exchange("http://localhost:8080/admin/realms/resourceServer/users/" + id,
                    HttpMethod.GET, request, KeycloakDataJson.class);

            Person tmp = personRepository.findByLogin(re.getBody().getUsername());

            ResponseEntity<String> delete = new RestTemplate().exchange("http://localhost:8080/admin/realms/resourceServer/users/" + id,
                        HttpMethod.DELETE, request, String.class);

            personService.removePerson(tmp.getId());

        }
        else
        {
            System.out.println("Nie masz uprawnien admina");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().build();
    }

    private void SetVariablesInPerson(Person original, Person mod){
        if (!(mod.getFirstName() == null))
            original.setFirstName(mod.getFirstName());
        if (!(mod.getLastName() == null))
            original.setLastName(mod.getLastName());
        if (!(mod.getEmail() == null))
            original.setEmail(mod.getEmail());
        if (!(mod.getTelephone() == null))
            original.setTelephone(mod.getTelephone());
        if (!(mod.getLogin() == null))
            original.setLogin(mod.getLogin());
        if (!(mod.getPassword() == null))
            original.setPassword(mod.getPassword());
    }

}
