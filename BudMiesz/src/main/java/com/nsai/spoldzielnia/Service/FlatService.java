package com.nsai.spoldzielnia.Service;

import com.nsai.spoldzielnia.Entity.Building;
import com.nsai.spoldzielnia.Entity.Flat;
import com.nsai.spoldzielnia.Entity.FlatCharges;
import com.nsai.spoldzielnia.Repository.BuildingRepository;
import com.nsai.spoldzielnia.Repository.FlatChargesRepository;
import com.nsai.spoldzielnia.Repository.FlatRepository;
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
public class FlatService {

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private FlatRepository flatRepository;
    //private BuildingRepository buildingRepository;
    //private BuildingService buildingService;
    @Autowired
    private FlatChargesService flatChargesService;
    @Autowired
    private FlatChargesRepository flatChargesRepository;

    @Autowired
    private AuthService authService;


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
    public void removeFlat(long id, String token) {
        System.out.println("Usuwanie mieszkania service "+id);

        //usuwanie mieszkanców
        ResponseEntity re= authService.nExchange("http://localhost:8000/managers-locators-service/deleteAllLocatorsFromFlat/"+id, token);
        System.out.println(re.getStatusCodeValue());
        if(re.getStatusCodeValue()!=200)return;//jesli nie 200 wycofaj


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
