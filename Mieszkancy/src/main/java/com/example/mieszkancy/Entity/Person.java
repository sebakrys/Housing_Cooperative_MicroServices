package com.example.mieszkancy.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @NotNull
    @Column(name = "firstName", nullable = false)
    @Size(min = 4, max = 30)
    private String firstName;

    @NotNull
    @Column(name = "LastName", nullable = false)
    @Size(min = 4, max = 30)
    private String lastName;

    @NotNull
    private String email;

    @Size(min = 9,max = 9)
    private String telephone;

    @NotNull
    @Column(unique = true)
    private String login;

    @NotNull
    private String password;

    private boolean enabled;
    private boolean dept;
    private boolean valid;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<PersonRole> personRole = new HashSet<PersonRole>();


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isDept() {
        return dept;
    }

    public void setDept(boolean dept) {
        this.dept = dept;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Set<PersonRole> getPersonRole() {
        return personRole;
    }

    public void setAppUserRole(Set<PersonRole> appUserRole) {
        this.personRole = appUserRole;
    }

}
