package com.nsai.spoldzielnia.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "flat")
public class Flat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;


    @ManyToOne
    @JoinColumn(name="building_id", nullable=false)
    @JsonIgnore
    private Building building;



    @OneToMany(cascade = CascadeType.ALL, mappedBy = "flat")
    //@LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<FlatCharges> flatCharges;

    private String flatNumber;
    //private String city;
    //private String street;
    //private String postalCode;


    public List<FlatCharges> getFlatCharges() {
        return flatCharges;
    }

    public void addFlatCharges(FlatCharges flatCharge) {
        flatCharges.add(flatCharge);
        flatCharge.setFlat(this);
    }

    public void removeFlatCharges(FlatCharges flatCharge) {
        flatCharges.remove(flatCharge);
        flatCharge.setFlat(null);
    }


    public void setFlatCharges(List<FlatCharges> flatCharges) {
        this.flatCharges = flatCharges;
        this.flatCharges.forEach(spFlatCharges -> spFlatCharges.setFlat(this));
    }




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

}
