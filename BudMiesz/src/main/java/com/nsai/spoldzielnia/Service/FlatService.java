package com.nsai.spoldzielnia.Service;

import com.nsai.spoldzielnia.Entity.Building;
import com.nsai.spoldzielnia.Entity.Flat;
import com.nsai.spoldzielnia.Repository.BuildingRepository;
import com.nsai.spoldzielnia.Repository.FlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FlatService {

    private FlatRepository flatRepository;
    private BuildingRepository buildingRepository;
    private BuildingService buildingService;
    private FlatChargesService flatChargesService;


    @Autowired
    public FlatService(FlatRepository flatRepository, BuildingRepository buildingRepository, BuildingService buildingService, FlatChargesService flatChargesService) {
        this.flatRepository = flatRepository;
        this.buildingRepository = buildingRepository;
        this.buildingService = buildingService;
        this.flatChargesService = flatChargesService;
    }






    @Transactional
    public void addFlat(Flat flat) {

        flatRepository.save(flat);
    }

    @Transactional
    public void editFlat(Flat flat) {

        flatRepository.save(flat);
    }

    @Transactional
    public List<Flat> listFlats() {
        return flatRepository.findAll();
    }

    @Transactional
    public Flat getFlat(long id) {
        return flatRepository.findById(id);
    }

    @Transactional
    public void removeFlat(long id) {
        System.out.println("Usuwanie mieszkania service "+id);

        //this.parent.dismissChild(this); //SYNCHRONIZING THE OTHER SIDE OF RELATIONSHIP
        //this.parent = null;


        Flat tempFlat = getFlat(id);
        Building tempBuilding = buildingService.getBuilding(tempFlat.getBuilding().getId());


        //getFlat(id).setBuilding(null);
        //tempBuilding.removeFlat(tempFlat);
        tempFlat.getBuilding().removeFlat(tempFlat);
        flatRepository.delete(tempFlat);

        System.out.println("mieszkania powinno byc usuniete service "+id);
    }

}
