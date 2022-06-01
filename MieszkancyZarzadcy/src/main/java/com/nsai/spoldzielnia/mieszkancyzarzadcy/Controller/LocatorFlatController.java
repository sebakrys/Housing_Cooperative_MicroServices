package com.nsai.spoldzielnia.mieszkancyzarzadcy.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.nsai.spoldzielnia.mieszkancyzarzadcy.Entity.LocatorFlat;
import com.nsai.spoldzielnia.mieszkancyzarzadcy.Service.AuthService;
import com.nsai.spoldzielnia.mieszkancyzarzadcy.Service.LocatorFlatService;
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
public class LocatorFlatController {

    private LocatorFlatService locatorFlatService;
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private AuthService authService;

    public LocatorFlatController(LocatorFlatService locatorFlatService) {
        this.locatorFlatService = locatorFlatService;
    }

    //POST
    @PostMapping(value = "/addNewLocatorFlat/{locatorId}/{flatId}")
    public ResponseEntity<LocatorFlat> addNewLocatorFlat(@PathVariable Long locatorId, @PathVariable Long flatId, @RequestHeader (name="Authorization") String token) {
        LocatorFlat locatorFlat = new LocatorFlat();
        locatorFlat.setLocatorId(locatorId);
        locatorFlat.setFlatId(flatId);

        //aaa weryfikacja czy jest admin lub manager dla budynku tego mieszkania
        boolean admin = authService.isAdmin(token);
        boolean managAccess = false;
        if(admin) managAccess = true;//jest adminem
        else {
            boolean manager = authService.isManager(token);
            long user_id = authService.getUserID(token);
            if(manager){//jesli jest managerem
                long manager_id = user_id;
                if(manager_id!=-1l) {
                    Long bId = authService.nGetForObjectLong("http://localhost:8000/building-flat-service/getFlatBuildingId/"+flatId, token);
                    boolean isManagBuild = authService.isManagerBuilding(manager_id, bId, token);
                    if(isManagBuild) managAccess = true;//jest managerem budynku tego mieszkania
                }
            }
        }
        if(!managAccess)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//jesli nie ma dostepu managerskiego


        try {
            //todo mozna zmodyfikowac na bardziej eleganckie rozwiazanie:         ResponseEntity re= authService.nExchange("http://localhost:8000/managers-locators-service/deleteAllManagersFromBuilding/"+id, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
            //sprawdzanie czy user istnieje
            if(authService.nExchange("http://localhost:8000/residents-flat-service/getPerson/"+locatorId, token).getStatusCodeValue()!=200) return ResponseEntity.notFound().build();
            //old restTemplate.getForObject("http://localhost:8000/residents-flat-service/getPerson/"+locatorId, String.class);
            //sprawdzanie czy Flat istnieje
            if(authService.nExchange("http://localhost:8000/building-flat-service/getFlat/"+flatId, token).getStatusCodeValue()!=200) return ResponseEntity.notFound().build();
            //old restTemplate.getForObject("http://localhost:8000/building-flat-service/getFlat/"+flatId, String.class);

            return ResponseEntity.ok(locatorFlatService.addLocatorToFlat(locatorFlat));
        }
        catch (final HttpClientErrorException e) {
            System.out.println(e.getStatusCode());
            System.out.println(e.getResponseBodyAsString());
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/getAllLocatorFlat")
    public ResponseEntity<List<LocatorFlat>> getAllLocatorFlat(@RequestHeader (name="Authorization") String token){

        //aaa weryfikacja czy jest admin
        boolean admin = authService.isAdmin(token);
        if(!admin) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok(locatorFlatService.listAllLocatorsFlat());
    }

    @GetMapping("/getAllFlatsFromLocator/{locatorId}")
    public ResponseEntity<List<Long>> getAllFlatsFromLocator(@PathVariable Long locatorId, @RequestHeader (name="Authorization") String token){

        //aaa weryfikacja czy jest admin lub manager dla budynku tego mieszkania
        boolean admin = authService.isAdmin(token);
        boolean adminAccess = false;
        boolean userAccess = false;
        if(admin) adminAccess = true;//jest adminem
        else {
            boolean manager = authService.isManager(token);
            boolean locator = authService.isUser(token);
            long user_id = authService.getUserID(token);
            if(locator && user_id==locatorId)userAccess=true;

        }
        if(!adminAccess && !userAccess)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//jesli nie ma dostepu managerskiego


        return ResponseEntity.ok(locatorFlatService.getAllFlatsFromLocator(locatorId));
    }

    @GetMapping("/getAllLocatorsFromFlat/{flatId}")
    public ResponseEntity<List<Long>> getAllLocatorsFromFlat(@PathVariable Long flatId, @RequestHeader (name="Authorization") String token){

        //aaa weryfikacja czy jest admin lub manager lub user
        boolean admin = authService.isAdmin(token);
        boolean manager = authService.isManager(token);
        boolean user = authService.isUser(token);
        if(!admin && !manager && !user)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//jesli nie ma zadnej roli


        return ResponseEntity.ok(locatorFlatService.getAllLocatorsFromFlat(flatId));
    }



    @GetMapping("/getLocatorFromFlat/{flatId}/{locatorId}")
    public ResponseEntity<Boolean> isLocatorFromFlat(@PathVariable Long flatId, @PathVariable Long locatorId, @RequestHeader (name="Authorization") String token){//todo jesli bedzie dzialac mozna dodac sprawdzanie tokena w przyszłości

        //aaa weryfikacja czy jest admin lub manager lub user
        boolean admin = authService.isAdmin(token);
        boolean manager = authService.isManager(token);
        boolean user = authService.isUser(token);
        if(!admin && !manager && !user)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//jesli nie ma zadnej roli

        boolean locatorFlat = locatorFlatService.isLocatorFromFlat(flatId, locatorId);
        if(!locatorFlat)return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(locatorFlat);
    }




    //DELETE
    @DeleteMapping("/deleteLocatorFlat/{id}")
    public ResponseEntity<LocatorFlat> deleteLocatorFlat(@PathVariable Long id, @RequestHeader (name="Authorization") String token) {

        //aaa weryfikacja czy jest admin
        boolean admin = authService.isAdmin(token);
        if(!admin) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        locatorFlatService.removeLocatorFlat(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteLocatorFromFlat/{locatorId}/{flatId}")
    public ResponseEntity<LocatorFlat> deleteLocatorFlat(@PathVariable Long locatorId, @PathVariable Long flatId, @RequestHeader (name="Authorization") String token) {
        //aaa weryfikacja czy jest admin lub manager dla budynku tego mieszkania
        boolean admin = authService.isAdmin(token);
        boolean managAccess = false;
        if(admin) managAccess = true;//jest adminem
        else {
            boolean manager = authService.isManager(token);
            long user_id = authService.getUserID(token);
            if(manager){//jesli jest managerem
                long manager_id = user_id;
                if(manager_id!=-1l) {
                    Long bId = authService.nGetForObjectLong("http://localhost:8000/building-flat-service/getFlatBuildingId/"+flatId, token);
                    boolean isManagBuild = authService.isManagerBuilding(manager_id, bId, token);
                    if(isManagBuild) managAccess = true;//jest managerem budynku tego mieszkania
                }
            }
        }
        if(!managAccess)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//jesli nie ma dostepu managerskiego

        locatorFlatService.removeLocatorFromFlat(locatorId, flatId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAllFlatsFromLocator/{locatorId}")
    public ResponseEntity<LocatorFlat> deleteAllFlatsFromLocator(@PathVariable Long locatorId, @RequestHeader (name="Authorization") String token) {

        //aaa weryfikacja czy jest admin lub user dla budynku tego mieszkania
        boolean admin = authService.isAdmin(token);
        boolean adminAccess = false;
        boolean userAccess = false;
        if(admin) adminAccess = true;//jest adminem
        else {
            boolean manager = authService.isManager(token);
            boolean locator = authService.isUser(token);
            long user_id = authService.getUserID(token);
            if(locator && user_id==locatorId)userAccess=true;

        }
        if(!adminAccess && !userAccess)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//jesli nie ma dostepu managerskiego


        locatorFlatService.removeAllFlatsFromLocator(locatorId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteAllLocatorsFromFlat/{flatId}")
    public ResponseEntity<LocatorFlat> deleteAllLocatorFromFlat(@PathVariable Long flatId, @RequestHeader (name="Authorization") String token) {
        //aaa weryfikacja czy jest admin lub manager dla budynku tego mieszkania
        boolean admin = authService.isAdmin(token);
        boolean managAccess = false;
        if(admin) managAccess = true;//jest adminem
        else {
            boolean manager = authService.isManager(token);
            long user_id = authService.getUserID(token);
            if(manager){//jesli jest managerem
                long manager_id = user_id;
                if(manager_id!=-1l) {
                    Long bId = authService.nGetForObjectLong("http://localhost:8000/building-flat-service/getFlatBuildingId/"+flatId, token);
                    boolean isManagBuild = authService.isManagerBuilding(manager_id, bId, token);
                    if(isManagBuild) managAccess = true;//jest managerem budynku tego mieszkania
                }
            }
        }
        if(!managAccess)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//jesli nie ma dostepu managerskiego

        locatorFlatService.removeAllLocatorsFromFlat(flatId);
        return ResponseEntity.ok().build();
    }



}
