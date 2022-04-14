package com.nsai.spoldzielnia.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsai.spoldzielnia.Entity.Building;
import com.nsai.spoldzielnia.Entity.Flat;
import com.nsai.spoldzielnia.Entity.Flat;
import com.nsai.spoldzielnia.Entity.FlatCharges;
import com.nsai.spoldzielnia.Repository.FlatChargesRepository;
import com.nsai.spoldzielnia.Repository.FlatRepository;
import com.nsai.spoldzielnia.Repository.FlatRepository;
import com.nsai.spoldzielnia.Service.BuildingService;
import com.nsai.spoldzielnia.Service.FlatChargesService;
import com.nsai.spoldzielnia.Service.FlatService;
import com.nsai.spoldzielnia.Service.FlatService;
import com.nsai.spoldzielnia.Validator.FlatValidator;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FlatController {
    
    @Autowired
    private FlatService flatService;

    @Autowired
    private BuildingService buildingService;

    private final FlatRepository flatRepository;
    @Autowired
    private  FlatChargesService flatChargesService;

    private FlatValidator flatValidator = new FlatValidator();

    public FlatController(FlatRepository flatRepository) {
        this.flatRepository = flatRepository;
    }

    //POST
    @PostMapping(value = "/addNewFlat")
    public ResponseEntity<Flat> addNewFlat(@RequestBody Flat flat, BindingResult result) throws JsonProcessingException {

        System.out.println(new ObjectMapper().writeValueAsString(flat));
        Building tmpBuilding = buildingService.getBuilding(flat.getBuilding().getId());
        flat.setBuilding(tmpBuilding);
        System.out.println(new ObjectMapper().writeValueAsString(flat));

        flatValidator.validate(flat, result);

        if(flat.getBuilding()==null){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        if (result.getErrorCount() == 0) {
            //dodawanie budynku
            System.out.println("dodawanie flat");
            return ResponseEntity.ok(flatService.addFlat(flat));
        }

        System.out.println("są bledy validatora");


        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    //PUT
    @PutMapping("/updateFlat")
    public ResponseEntity<Flat> updateFlat(@RequestBody Flat flat, BindingResult result) throws JsonProcessingException {

        if(flatService.getFlat(flat.getId())==null)return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

        System.out.println(new ObjectMapper().writeValueAsString(flat));
        Building tmpBuilding = buildingService.getBuilding(flat.getBuilding().getId());
        flat.setBuilding(tmpBuilding);
        System.out.println(new ObjectMapper().writeValueAsString(flat));

        flatValidator.validate(flat, result);


        if (result.getErrorCount() == 0) {
            System.out.println("edit flat");
            return ResponseEntity.ok(flatService.editFlat(flat));
        }


        System.out.println("są bledy validatora");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    @GetMapping("/getAllFlat")
    public List<Flat> getAllFlat(){
        return flatService.listFlats();
    }

    @GetMapping("/getFlat/{id}")
    public ResponseEntity<Flat> getFlatById(@PathVariable Long id){
        return flatRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping("/getFlatFlatCharges/{id}")
    public ResponseEntity<List<FlatCharges>> getFlatFlatChargesById(@PathVariable Long id) throws JsonProcessingException {
        Flat tmpFlat = flatService.getFlat(id);
        if(tmpFlat==null)return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();



        //old return ResponseEntity.ok(tmpFlat.getFlatCharges());
        return ResponseEntity.ok(flatChargesService.getAllFlatChargesFromFlat(id));
    }


    //DELETE
    @DeleteMapping("/deleteFlat/{flatId}")
    public ResponseEntity<Building> deleteFlat(@PathVariable Long flatId) {

        System.out.println("Usuwanie  flat "+flatId);

        if(flatService.getFlat(flatId)==null)return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

        flatService.removeFlat(flatId);
        return ResponseEntity.ok().build();
    }
}
