package com.nsai.spoldzielnia.mieszkancyzarzadcy.Controller;

import com.nsai.spoldzielnia.mieszkancyzarzadcy.Entity.ManagerBuilding;
import com.nsai.spoldzielnia.mieszkancyzarzadcy.Service.AuthService;
import com.nsai.spoldzielnia.mieszkancyzarzadcy.Service.ManagerBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private AuthService authService;

    public ManagerBuildingController(ManagerBuildingService managerBuildingService) {
        this.managerBuildingService = managerBuildingService;
    }

    //POST
    @PostMapping(value = "/addNewManagerBuilding/{managerId}/{buildingId}")
    public ResponseEntity<ManagerBuilding> addNewManagerBuilding(@PathVariable Long managerId, @PathVariable Long buildingId, @RequestHeader (name="Authorization") String token) {
        ManagerBuilding managerBuilding = new ManagerBuilding();
        managerBuilding.setManagerId(managerId);
        managerBuilding.setBuildingId(buildingId);

        //aaa weryfikacja czy jest admin
        boolean admin = authService.isAdmin(token);
        if(!admin) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        try {
            //todo mozna zmodyfikowac na bardziej eleganckie rozwiazanie:         ResponseEntity re= authService.nExchange("http://localhost:8000/managers-locators-service/deleteAllManagersFromBuilding/"+id, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
            //sprawdzanie czy user istnieje
            if(authService.nExchange("http://localhost:8000/residents-flat-service/getPerson/"+managerId, token).getStatusCodeValue()!=200) return ResponseEntity.notFound().build();
            //old restTemplate.getForObject("http://localhost:8000/residents-flat-service/getPerson/"+managerId, String.class);
            //sprawdzanie czy Building istnieje
            if(authService.nExchange("http://localhost:8000/building-flat-service/getBuilding/"+buildingId, token).getStatusCodeValue()!=200) return ResponseEntity.notFound().build();
            //old restTemplate.getForObject("http://localhost:8000/building-flat-service/getBuilding/"+buildingId, String.class);

            try {
                ManagerBuilding mb = managerBuildingService.addManagerToBuilding(managerBuilding);
                if(mb!=null)return ResponseEntity.ok(mb);
                else return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }catch (Exception e){
                System.out.println(e);
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            //return ResponseEntity.ok(managerBuildingService.addManagerToBuilding(managerBuilding));
        }
        catch (Exception e) {
            System.out.println(e);
            //System.out.println(e.getResponseBodyAsString());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getAllManagerBuilding")
    public ResponseEntity<List<ManagerBuilding>> getAllManagerBuilding(@RequestHeader (name="Authorization") String token){

        //aaa weryfikacja czy jest admin lub manager lub user
        boolean admin = authService.isAdmin(token);
        boolean manager = authService.isManager(token);
        boolean user = authService.isUser(token);
        if(!admin && !manager && !user)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//jesli nie ma zadnej roli


        return ResponseEntity.ok(managerBuildingService.listAllManagersBuilding());
    }

    @GetMapping("/getAllBuildingsFromManager/{managerId}")
    public ResponseEntity<List<Long>> getAllBuildingsFromManager(@PathVariable Long managerId, @RequestHeader (name="Authorization") String token){


        //aaa weryfikacja czy jest admin lub manager lub user
        boolean admin = authService.isAdmin(token);
        boolean manager = authService.isManager(token);
        boolean user = authService.isUser(token);
        if(!admin && !manager && !user)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//jesli nie ma zadnej roli


        return ResponseEntity.ok(managerBuildingService.getAllBuildingsFromManager(managerId));
    }

    @GetMapping("/getAllManagersFromBuilding/{buildingId}")
    public ResponseEntity<List<Long>> getAllManagersFromBuilding(@PathVariable Long buildingId, @RequestHeader (name="Authorization") String token){
        //aaa weryfikacja czy jest admin lub manager lub user
        boolean admin = authService.isAdmin(token);
        boolean manager = authService.isManager(token);
        boolean user = authService.isUser(token);
        if(!admin && !manager && !user)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//jesli nie ma zadnej roli

        return ResponseEntity.ok(managerBuildingService.getAllManagersFromBuilding(buildingId));
    }



    @GetMapping("/getManagerFromBuilding/{buildingId}/{managerId}")
    public ResponseEntity<Boolean> isManagerFromBuilding(@PathVariable Long buildingId, @PathVariable Long managerId, @RequestHeader (name="Authorization") String token){
        //aaa weryfikacja czy jest admin lub manager lub user
        boolean admin = authService.isAdmin(token);
        boolean manager = authService.isManager(token);
        boolean user = authService.isUser(token);
        if(!admin && !manager && !user)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//jesli nie ma zadnej roli

        boolean managBuild = managerBuildingService.isManagerFromBuilding(buildingId, managerId);
        if(!managBuild)return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(managBuild);
    }



    //DELETE
    @DeleteMapping("/deleteManagerBuilding/{id}")
    public ResponseEntity<ManagerBuilding> deleteManagerBuilding(@PathVariable Long id, @RequestHeader (name="Authorization") String token) {
        //aaa weryfikacja czy jest admin
        boolean admin = authService.isAdmin(token);
        if(!admin) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        managerBuildingService.removeManagerBuilding(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteManagerFromBuilding/{managerId}/{buildingId}")
    public ResponseEntity<ManagerBuilding> deleteManagerBuilding(@PathVariable Long managerId, @PathVariable Long buildingId, @RequestHeader (name="Authorization") String token) {
        //aaa weryfikacja czy jest admin
        boolean admin = authService.isAdmin(token);
        if(!admin) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        managerBuildingService.removeManagerFromBuilding(managerId, buildingId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAllBuildingsFromManager/{managerId}")
    public ResponseEntity<ManagerBuilding> deleteAllBuildingsFromManager(@PathVariable Long managerId, @RequestHeader (name="Authorization") String token) {
        //aaa weryfikacja czy jest admin
        boolean admin = authService.isAdmin(token);
        if(!admin) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        managerBuildingService.removeAllBuildingsFromManager(managerId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAllManagersFromBuilding/{buildingId}")
    public ResponseEntity<ManagerBuilding> deleteAllManagerFromBuilding(@PathVariable Long buildingId, @RequestHeader (name="Authorization") String token) {
        //aaa weryfikacja czy jest admin
        boolean admin = authService.isAdmin(token);
        if(!admin) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        managerBuildingService.removeAllManagersFromBuilding(buildingId);
        return ResponseEntity.ok().build();
    }
}
