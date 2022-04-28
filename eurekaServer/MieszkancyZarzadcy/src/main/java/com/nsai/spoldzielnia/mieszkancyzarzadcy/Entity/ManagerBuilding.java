package com.nsai.spoldzielnia.mieszkancyzarzadcy.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;



import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "manager_building")
public class ManagerBuilding {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    long manager_id;
    long building_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getManager_id() {
        return manager_id;
    }

    public void setManager_id(long manager_id) {
        this.manager_id = manager_id;
    }

    public long getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(long building_id) {
        this.building_id = building_id;
    }
}
