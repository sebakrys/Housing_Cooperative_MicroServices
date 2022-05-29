package com.example.mieszkancy.Repository;

import com.example.mieszkancy.Entity.PersonRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface PersonRoleRepository extends JpaRepository<PersonRole, Long> {
    PersonRole findByRole(String role);
}
