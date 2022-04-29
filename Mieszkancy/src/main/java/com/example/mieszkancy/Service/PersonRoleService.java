package com.example.mieszkancy.Service;


import com.example.mieszkancy.Entity.PersonRole;
import com.example.mieszkancy.Repository.PersonRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonRoleService {

    @Autowired
    private PersonRoleRepository personRoleRepository;

    @Transactional
    public PersonRole addPersonRole(PersonRole personRole) { return personRoleRepository.save(personRole);}

    @Transactional
    public List<PersonRole> listPersonRole(){ return personRoleRepository.findAll(); }

    @Transactional
    public PersonRole getPersonRole(long id) { return personRoleRepository.getById(id); }
}
