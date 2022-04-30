package com.nsai.spoldzielnia.mieszkancyzarzadcy.Controller;

import com.nsai.spoldzielnia.mieszkancyzarzadcy.Entity.ManagerBuilding;
import com.nsai.spoldzielnia.mieszkancyzarzadcy.Service.ManagerBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ManagerBuildingController {


    private ManagerBuildingService managerBuildingService;
    private RestTemplate restTemplate = new RestTemplate();

    public ManagerBuildingController(ManagerBuildingService managerBuildingService) {
        this.managerBuildingService = managerBuildingService;
    }

    //POST
    @PostMapping(value = "/addNewManagerBuilding/{managerId}/{buildingId}")
    public ResponseEntity<ManagerBuilding> addNewManagerBuilding(@PathVariable Long managerId, @PathVariable Long buildingId) {
        ManagerBuilding managerBuilding = new ManagerBuilding();
        managerBuilding.setManagerId(managerId);
        managerBuilding.setBuildingId(buildingId);

        try {
            //todo mozna zmodyfikowac na bardziej eleganckie rozwiazanie:         ResponseEntity re= restTemplate.exchange("http://localhost:8000/managers-locators-service/deleteAllManagersFromBuilding/"+id, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
            //sprawdzanie czy user istnieje
            restTemplate.getForObject("http://localhost:8000/residents-flat-service/getPerson/"+managerId, String.class);
            //sprawdzanie czy Building istnieje
            restTemplate.getForObject("http://localhost:8000/building-flat-service/getBuilding/"+buildingId, String.class);

            return ResponseEntity.ok(managerBuildingService.addManagerToBuilding(managerBuilding));
        }
        catch (final HttpClientErrorException e) {
            System.out.println(e.getStatusCode());
            System.out.println(e.getResponseBodyAsString());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getAllManagerBuilding")
    public ResponseEntity<List<ManagerBuilding>> getAllManagerBuilding(){
        return ResponseEntity.ok(managerBuildingService.listAllManagersBuilding());
    }

    @GetMapping("/getAllBuildingsFromManager/{managerId}")
    public ResponseEntity<List<Long>> getAllBuildingsFromManager(@PathVariable Long managerId){
        return ResponseEntity.ok(managerBuildingService.getAllBuildingsFromManager(managerId));
    }

    @GetMapping("/getAllManagersFromBuilding/{buildingId}")
    public ResponseEntity<List<Long>> getAllManagersFromBuilding(@PathVariable Long buildingId){
        return ResponseEntity.ok(managerBuildingService.getAllManagersFromBuilding(buildingId));
    }

    //DELETE
    @DeleteMapping("/deleteManagerBuilding/{id}")
    public ResponseEntity<ManagerBuilding> deleteManagerBuilding(@PathVariable Long id) {
        managerBuildingService.removeManagerBuilding(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteManagerFromBuilding/{managerId}/{buildingId}")
    public ResponseEntity<ManagerBuilding> deleteManagerBuilding(@PathVariable Long managerId, @PathVariable Long buildingId) {
        managerBuildingService.removeManagerFromBuilding(managerId, buildingId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAllBuildingsFromManager/{managerId}")
    public ResponseEntity<ManagerBuilding> deleteAllBuildingsFromManager(@PathVariable Long managerId) {
        managerBuildingService.removeAllBuildingsFromManager(managerId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAllManagersFromBuilding/{buildingId}")
    public ResponseEntity<ManagerBuilding> deleteAllManagerFromBuilding(@PathVariable Long buildingId) {
        managerBuildingService.removeAllManagersFromBuilding(buildingId);
        return ResponseEntity.ok().build();
    }
}
