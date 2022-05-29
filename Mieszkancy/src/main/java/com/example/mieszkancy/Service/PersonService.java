package com.example.mieszkancy.Service;


import com.example.mieszkancy.Entity.Person;
import com.example.mieszkancy.Repository.PersonRepository;
import com.example.mieszkancy.Repository.PersonRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PersonService {

    private PersonRepository personRepository;
    private PersonRoleRepository personRoleRepository;

    @Autowired
    public PersonService(PersonRepository personRepository, PersonRoleRepository personRoleRepository){

        this.personRepository = personRepository;
        this.personRoleRepository = personRoleRepository;
    }

    @Transactional
    public Person addPerson(Person person){
        person.getPersonRole().add(personRoleRepository.findByRole("ROLE_USER"));
        return personRepository.save(person);
    }

    @Transactional
    public Person editPerson(Person person){
        person.getPersonRole().add(personRoleRepository.findByRole("ROLE_USER"));
        return personRepository.save(person);
    }

//    private String hashPassword(String password){
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        return passwordEncoder.encode(password);
//    }

    @Transactional
    public List<Person> listPersons(){ return personRepository.findAll();}

    @Transactional
    public void removePerson (long id) {personRepository.deleteById(id);}

    @Transactional
    public Person getPerson(long id) { return personRepository.findById(id);}

    @Transactional
    public Person findByLogin(String login) {
        return personRepository.findByLogin(login);
    }

    @Transactional
    public Person findByEmail(String email){
        return personRepository.findByEmail(email);
    }
}
