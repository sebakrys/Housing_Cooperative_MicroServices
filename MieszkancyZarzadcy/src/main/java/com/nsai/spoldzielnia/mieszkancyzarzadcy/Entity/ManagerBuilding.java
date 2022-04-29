package com.nsai.spoldzielnia.mieszkancyzarzadcy.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;



import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "manager_building", uniqueConstraints={
        @UniqueConstraint(columnNames = {"managerId", "buildingId"})
})
public class ManagerBuilding {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    long managerId;
    long buildingId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getManagerId() {
        return managerId;
    }

    public void setManagerId(long managerId) {
        this.managerId = managerId;
    }

    public long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(long buildingId) {
        this.buildingId = buildingId;
    }
}
