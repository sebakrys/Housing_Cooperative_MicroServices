package com.nsai.spoldzielnia.Service;

import com.nsai.spoldzielnia.Entity.Building;
import com.nsai.spoldzielnia.Repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BuildingService {

    private BuildingRepository buildingRepository;

    @Autowired
    public BuildingService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }



    @Transactional
    public Building addBuilding(Building building) {

        return buildingRepository.save(building);
    }

    @Transactional
    public Building editBuilding(Building building) {

        return buildingRepository.save(building);
    }

    @Transactional
    public List<Building> listBuildings() {
        return buildingRepository.findAll();
    }

    @Transactional
    public Building getBuilding(long id) {
        return buildingRepository.findById(id);
    }

    @Transactional
    public Building getBuildingByName(String name) {
        return buildingRepository.findByNazwa(name);
    }

    @Transactional
    public void removeBuilding(long id) {
        buildingRepository.deleteById(id);
    }
    
}
