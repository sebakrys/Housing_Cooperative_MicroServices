package com.nsai.spoldzielnia.Service;

import com.nsai.spoldzielnia.Entity.Building;
import com.nsai.spoldzielnia.Entity.Flat;
import com.nsai.spoldzielnia.Entity.FlatCharges;
import com.nsai.spoldzielnia.Repository.BuildingRepository;
import com.nsai.spoldzielnia.Repository.FlatChargesRepository;
import com.nsai.spoldzielnia.Repository.FlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
public class FlatService {

    @Autowired
    private FlatRepository flatRepository;
    //private BuildingRepository buildingRepository;
    //private BuildingService buildingService;
    @Autowired
    private FlatChargesService flatChargesService;
    @Autowired
    private FlatChargesRepository flatChargesRepository;




    @Transactional
    public Flat addFlat(Flat flat) {

        return flatRepository.save(flat);
    }

    @Transactional
    public Flat editFlat(Flat flat) {

        return flatRepository.save(flat);
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

        // TODO usuwanie mieszkanców

        Flat tempFlat = getFlat(id);

        //usuwanie podległych flatcharges
        List<FlatCharges> fChargesList = tempFlat.getFlatCharges();
        for (Iterator<FlatCharges> fch = fChargesList.iterator(); fch.hasNext();) {
            FlatCharges tmpFlatCharges = fch.next();
            System.out.println("usuwanie localCharges: "+tmpFlatCharges.getId());
            flatChargesService.removeFlatCharges(tmpFlatCharges.getId());
        }

        flatRepository.delete(tempFlat);

        System.out.println("mieszkania powinno byc usuniete service "+id);
    }

}
