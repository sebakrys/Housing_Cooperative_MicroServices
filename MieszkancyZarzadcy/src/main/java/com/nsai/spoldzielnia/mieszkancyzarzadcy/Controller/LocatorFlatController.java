package com.nsai.spoldzielnia.mieszkancyzarzadcy.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.nsai.spoldzielnia.mieszkancyzarzadcy.Entity.LocatorFlat;
import com.nsai.spoldzielnia.mieszkancyzarzadcy.Service.LocatorFlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@RestController
public class LocatorFlatController {

    private LocatorFlatService locatorFlatService;
    private RestTemplate restTemplate = new RestTemplate();

    public LocatorFlatController(LocatorFlatService locatorFlatService) {
        this.locatorFlatService = locatorFlatService;
    }

    //POST
    @PostMapping(value = "/addNewLocatorFlat/{locatorId}/{flatId}")
    public ResponseEntity<LocatorFlat> addNewLocatorFlat(@PathVariable Long locatorId, @PathVariable Long flatId) {
        LocatorFlat locatorFlat = new LocatorFlat();
        locatorFlat.setLocatorId(locatorId);
        locatorFlat.setFlatId(flatId);

        try {
            //todo mozna zmodyfikowac na bardziej eleganckie rozwiazanie:         ResponseEntity re= restTemplate.exchange("http://localhost:8000/managers-locators-service/deleteAllManagersFromBuilding/"+id, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
            //sprawdzanie czy user istnieje
            restTemplate.getForObject("http://localhost:8000/residents-flat-service/getPerson/"+locatorId, String.class);
            //sprawdzanie czy Flat istnieje
            restTemplate.getForObject("http://localhost:8000/building-flat-service/getFlat/"+flatId, String.class);

            return ResponseEntity.ok(locatorFlatService.addLocatorToFlat(locatorFlat));
        }
        catch (final HttpClientErrorException e) {
            System.out.println(e.getStatusCode());
            System.out.println(e.getResponseBodyAsString());
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/getAllLocatorFlat")
    public ResponseEntity<List<LocatorFlat>> getAllLocatorFlat(){
        return ResponseEntity.ok(locatorFlatService.listAllLocatorsFlat());
    }

    @GetMapping("/getAllFlatsFromLocator/{locatorId}")
    public ResponseEntity<List<Long>> getAllFlatsFromLocator(@PathVariable Long locatorId){
        return ResponseEntity.ok(locatorFlatService.getAllFlatsFromLocator(locatorId));
    }

    @GetMapping("/getAllLocatorsFromFlat/{flatId}")
    public ResponseEntity<List<Long>> getAllLocatorsFromFlat(@PathVariable Long flatId){
        return ResponseEntity.ok(locatorFlatService.getAllLocatorsFromFlat(flatId));
    }

    //DELETE
    @DeleteMapping("/deleteLocatorFlat/{id}")
    public ResponseEntity<LocatorFlat> deleteLocatorFlat(@PathVariable Long id) {
        locatorFlatService.removeLocatorFlat(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteLocatorFromFlat/{locatorId}/{flatId}")
    public ResponseEntity<LocatorFlat> deleteLocatorFlat(@PathVariable Long locatorId, @PathVariable Long flatId) {
        locatorFlatService.removeLocatorFromFlat(locatorId, flatId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAllFlatsFromLocator/{locatorId}")
    public ResponseEntity<LocatorFlat> deleteAllFlatsFromLocator(@PathVariable Long locatorId) {
        locatorFlatService.removeAllFlatsFromLocator(locatorId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAllLocatorsFromFlat/{flatId}")
    public ResponseEntity<LocatorFlat> deleteAllLocatorFromFlat(@PathVariable Long flatId) {
        locatorFlatService.removeAllLocatorsFromFlat(flatId);
        return ResponseEntity.ok().build();
    }



}
