package com.nsai.spoldzielnia.Service;

import com.nsai.spoldzielnia.Entity.Building;
import com.nsai.spoldzielnia.Entity.Flat;
import com.nsai.spoldzielnia.Repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
public class BuildingService {

    private BuildingRepository buildingRepository;

    private FlatService flatService;

    @Autowired
    public BuildingService(BuildingRepository buildingRepository, FlatService flatService) {
        this.buildingRepository = buildingRepository;
        this.flatService = flatService;
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

        /* TODO usuwanie zarządców
        List<SpUserApp> zarzadcyBudynku = userService.getUserAppByBuilding(buildingId);
        SpBuilding building = buildingService.getBuilding(buildingId);
        for (SpUserApp tempZarz : zarzadcyBudynku) {
            //usuwanie zarzadców
            userService.removeUserBuilding(tempZarz, building);
        }*/
        Building tmpBuilding = getBuilding(id);

        List<Flat> buildingFlats = tmpBuilding.getFlat();
        for (Iterator<Flat> f = buildingFlats.iterator(); f.hasNext();) {
            Flat tmpFlat = f.next();
            System.out.println("usuwanie Flat: "+tmpFlat.getId());
            flatService.removeFlat(tmpFlat.getId());

        }
        buildingRepository.deleteById(id);
    }
    
}
