package com.nsai.spoldzielnia.mieszkancyzarzadcy.Entity;


import javax.persistence.*;

@Entity
@Table(name = "locator_flat")
public class LocatorFlat {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    long locator_id;
    long flat_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLocator_id() {
        return locator_id;
    }

    public void setLocator_id(long locator_id) {
        this.locator_id = locator_id;
    }

    public long getFlat_id() {
        return flat_id;
    }

    public void setFlat_id(long flat_id) {
        this.flat_id = flat_id;
    }
}
