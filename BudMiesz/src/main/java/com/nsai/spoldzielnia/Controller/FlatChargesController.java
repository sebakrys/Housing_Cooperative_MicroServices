package com.nsai.spoldzielnia.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsai.spoldzielnia.Entity.Building;
import com.nsai.spoldzielnia.Entity.Flat;
import com.nsai.spoldzielnia.Entity.FlatCharges;
import com.nsai.spoldzielnia.Repository.FlatChargesRepository;
import com.nsai.spoldzielnia.Service.BuildingService;
import com.nsai.spoldzielnia.Service.FlatChargesService;
import com.nsai.spoldzielnia.Service.FlatService;
import com.nsai.spoldzielnia.Validator.BuildingValidator;
import com.nsai.spoldzielnia.Validator.FlatChargesValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FlatChargesController {

    public static double cprad_stawka = 0.65;
    public static double cgaz_stawka = 1.3;
    public static double cwoda_ciepla_stawka = 35.0;
    public static double cwoda_zimna_stawka = 6.0;
    public static double cscieki_stawka = 9.1;
    public static double cogrzewanie_stawka = 0.3;
    public static double cfunduszRemontowy_stawka = 2.00;

    public static double prad_ryczalt = 65.0;//kWh na osobe
    public static double gaz_ryczalt = 16;//m3 na osobe
    public static double woda_ciepla_ryczalt = 0.500; //m3 na osobe
    public static double woda_zimna_ryczalt = 1.000; // m3 na osobe
    public static double scieki_ryczalt = 1.500; // m3 na osobe
    public static double ogrzewanie_ryczalt = 50;// kwh na osobe
    public static double funduszRemontowy_ryczalt = 60;// średnio na mieszkanie

    @Autowired
    private FlatChargesService flatChargesService;
    @Autowired
    private FlatService flatService;

    private final FlatChargesRepository flatChargesRepository;

    private FlatChargesValidator flatChargesValidator = new FlatChargesValidator();

    public FlatChargesController(FlatChargesRepository flatChargesRepository) {
        this.flatChargesRepository = flatChargesRepository;
    }

    //POST
    @PostMapping(value = "/addNewFlatCharges")
    public ResponseEntity<FlatCharges> addNewFlatCharges(@RequestBody FlatCharges flatCharges, BindingResult result) throws JsonProcessingException {

        System.out.println(new ObjectMapper().writeValueAsString(flatCharges));
        Flat tmpFlat = flatService.getFlat(flatCharges.getFlat().getId());
        flatCharges.setFlat(tmpFlat);
        System.out.println(new ObjectMapper().writeValueAsString(flatCharges));

        flatChargesValidator.validate(flatCharges, result);

        if(flatCharges.getFlat()==null){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        if (result.getErrorCount() == 0) {
            //dodawanie budynku
            System.out.println("dodawanie flatcharges");
            return ResponseEntity.ok(flatChargesService.addFlatCharges(flatCharges));
        }

        System.out.println("są bledy validatora");


        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    //PUT
    @PutMapping("/updateFlatCharges")
    public ResponseEntity<FlatCharges> updateFlatCharges(@RequestBody FlatCharges flatCharges, BindingResult result) throws JsonProcessingException {

        Flat tmpFlat = flatService.getFlat(flatCharges.getFlat().getId());
        flatCharges.setFlat(tmpFlat);
        System.out.println(new ObjectMapper().writeValueAsString(flatCharges));

        flatChargesValidator.validate(flatCharges, result);


        if (result.getErrorCount() == 0) {
            System.out.println("edit flatCharges");
            return ResponseEntity.ok(flatChargesService.editFlatCharges(flatCharges));
        }


        System.out.println("są bledy validatora");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    @GetMapping("/getAllFlatCharges")
    public List<FlatCharges> getAllFlatCharges(){
        return flatChargesService.listFlatCharges();
    }

    @GetMapping("/getFlatCharges/{id}")
    public ResponseEntity<FlatCharges> getFlatChargesById(@PathVariable Long id){
        return flatChargesRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }


    //DELETE
    @DeleteMapping("/deleteFlatCharges/{flatChargesId}")
    public ResponseEntity<Building> deleteFlatCharges(@PathVariable Long flatChargesId) {

        System.out.println("Usuwanie  flatCharges "+flatChargesId);



        flatChargesService.removeFlatCharges(flatChargesId);
        return ResponseEntity.ok().build();
    }


}
