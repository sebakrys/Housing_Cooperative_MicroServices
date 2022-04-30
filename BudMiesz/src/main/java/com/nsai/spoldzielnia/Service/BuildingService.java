package com.nsai.spoldzielnia.Service;

import com.nsai.spoldzielnia.Entity.Building;
import com.nsai.spoldzielnia.Entity.Flat;
import com.nsai.spoldzielnia.Repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;

@Service
public class BuildingService {

    private RestTemplate restTemplate = new RestTemplate();

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


        //usuwanie zarządców
        ResponseEntity re= restTemplate.exchange("http://localhost:8000/managers-locators-service/deleteAllManagersFromBuilding/"+id, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
        System.out.println(re.getStatusCodeValue());
        if(re.getStatusCodeValue()!=200)return;//jesli nie 200 wycofaj

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
