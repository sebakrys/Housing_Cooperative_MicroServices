package com.example.mieszkancy.Repository;

import com.example.mieszkancy.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findById(long id);
    Person findByLogin(String login);
    Person findByEmail(String email);

    void deleteById(long id);
}
