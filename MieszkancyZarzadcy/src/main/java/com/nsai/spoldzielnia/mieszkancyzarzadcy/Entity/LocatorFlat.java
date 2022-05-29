package com.nsai.spoldzielnia.mieszkancyzarzadcy.Entity;


import javax.persistence.*;

@Entity
@Table(name = "locator_flat", uniqueConstraints={
        @UniqueConstraint(columnNames = {"locatorId", "flatId"})
})
public class LocatorFlat {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    long locatorId;
    long flatId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLocatorId() {
        return locatorId;
    }

    public void setLocatorId(long locatorId) {
        this.locatorId = locatorId;
    }

    public long getFlatId() {
        return flatId;
    }

    public void setFlatId(long flatId) {
        this.flatId = flatId;
    }
}
