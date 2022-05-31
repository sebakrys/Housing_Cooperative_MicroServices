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
import com.nsai.spoldzielnia.Service.*;
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
import java.util.Optional;

@RestController
public class FlatController {
    
    @Autowired
    private FlatService flatService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private AuthService authService;

    private final FlatRepository flatRepository;
    @Autowired
    private  FlatChargesService flatChargesService;

    private FlatValidator flatValidator = new FlatValidator();

    public FlatController(FlatRepository flatRepository) {
        this.flatRepository = flatRepository;
    }

    //POST
    @PostMapping(value = "/addNewFlat")
    public ResponseEntity<Flat> addNewFlat(@RequestBody Flat flat, BindingResult result, @RequestHeader (name="Authorization") String token) throws JsonProcessingException {

        System.out.println(new ObjectMapper().writeValueAsString(flat));
        Building tmpBuilding = buildingService.getBuilding(flat.getBuilding().getId());


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
                    boolean isManagBuild = authService.isManagerBuilding(manager_id, flat.getBuilding().getId());
                    if(isManagBuild) managAccess = true;//jest managerem budynku tego mieszkania
                }
            }
        }
        if(!managAccess)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//jesli nie ma dostepu managerskiego


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
    public ResponseEntity<Flat> updateFlat(@RequestBody Flat flat, BindingResult result, @RequestHeader (name="Authorization") String token) throws JsonProcessingException {

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
                    boolean isManagBuild = authService.isManagerBuilding(manager_id, flat.getBuilding().getId());
                    if(isManagBuild) managAccess = true;//jest managerem budynku tego mieszkania
                }
            }
        }
        if(!managAccess)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//jesli nie ma dostepu managerskiego


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
    public ResponseEntity<List<Flat>> getAllFlat(@RequestHeader (name="Authorization") String token){
        //aaa weryfikacja czy jest admin
        boolean admin = authService.isAdmin(token);
        if(!admin) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(flatService.listFlats());
    }

    @GetMapping("/getFlat/{id}")
    public ResponseEntity<Flat> getFlatById(@PathVariable Long id, @RequestHeader (name="Authorization") String token){

        Optional<Flat> optFlat = flatRepository.findById(id);
        Flat tmpFlat = optFlat.get();

        //aaa weryfikacja czy jest admin lub manager lub locator tego mieszkania
        boolean admin = authService.isAdmin(token);
        boolean managAccess = false;
        boolean locatorAccess = false;
        if(admin) managAccess = true;//jest adminem
        else {
            boolean manager = authService.isManager(token);
            boolean locator = authService.isUser(token);
            long user_id = authService.getUserID(token);
            if(locator){//jest locatorerm
                long locator_id = user_id;
                if(locator_id!=-1l) {
                    boolean isLocatorFlat = authService.isLocatorFlat(locator_id, id);
                    if(isLocatorFlat) locatorAccess = true;//jest locatorem tego mieszkania
                }
            }
            if(manager){//jesli jest managerem
                long manager_id = user_id;
                if(manager_id!=-1l) {
                    boolean isManagBuild = authService.isManagerBuilding(manager_id, tmpFlat.getBuilding().getId());
                    if(isManagBuild) managAccess = true;//jest managerem budynku tego mieszkania
                }
            }
        }
        if(!managAccess && !locatorAccess)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//jesli nie ma dostepu zadnego

        return optFlat
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping("/getFlatBuildingId/{id}")
    public ResponseEntity<Long> getFlatBuildingById(@PathVariable Long flat_id, @RequestHeader (name="Authorization") String token){


        //aaa weryfikacja czy jest admin lub manager dla budynku tego mieszkania
        boolean admin = authService.isAdmin(token);
        boolean manager = authService.isManager(token);
        boolean managAccess = false;
        if(admin) managAccess = true;//jest adminem
        if(manager) managAccess = true;//jest manager

        if(!managAccess)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//jesli nie ma dostepu managerskiego


        Flat tmpFlat = flatService.getFlat(flat_id);
        if(tmpFlat==null)return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();



        return ResponseEntity.ok(tmpFlat.getBuilding().getId());
    }

    @GetMapping("/getFlatFlatCharges/{id}")
    public ResponseEntity<List<FlatCharges>> getFlatFlatChargesById(@PathVariable Long id, @RequestHeader (name="Authorization") String token) throws JsonProcessingException {
        Flat tmpFlat = flatService.getFlat(id);
        if(tmpFlat==null)return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();


        //aaa weryfikacja czy jest admin lub manager lub locator tego mieszkania
        boolean admin = authService.isAdmin(token);
        boolean managAccess = false;
        boolean locatorAccess = false;
        if(admin) managAccess = true;//jest adminem
        else {
            boolean manager = authService.isManager(token);
            boolean locator = authService.isUser(token);
            long user_id = authService.getUserID(token);
            if(locator){//jest locatorerm
                long locator_id = user_id;
                if(locator_id!=-1l) {
                    boolean isLocatorFlat = authService.isLocatorFlat(locator_id, id);
                    if(isLocatorFlat) locatorAccess = true;//jest locatorem tego mieszkania
                }
            }
            if(manager){//jesli jest managerem
                long manager_id = user_id;
                if(manager_id!=-1l) {
                    boolean isManagBuild = authService.isManagerBuilding(manager_id, tmpFlat.getBuilding().getId());
                    if(isManagBuild) managAccess = true;//jest managerem budynku tego mieszkania
                }
            }
        }
        if(!managAccess && !locatorAccess)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//jesli nie ma dostepu zadnego




        //old return ResponseEntity.ok(tmpFlat.getFlatCharges());
        return ResponseEntity.ok(flatChargesService.getAllFlatChargesFromFlat(id));
    }


    //DELETE
    @DeleteMapping("/deleteFlat/{flatId}")
    public ResponseEntity<Building> deleteFlat(@PathVariable Long flatId, @RequestHeader (name="Authorization") String token) {
        Flat tmpFlat = flatService.getFlat(flatId);
        if(tmpFlat==null)return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

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
                    boolean isManagBuild = authService.isManagerBuilding(manager_id, tmpFlat.getBuilding().getId());
                    if(isManagBuild) managAccess = true;//jest managerem budynku tego mieszkania
                }
            }
        }
        if(!managAccess)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//jesli nie ma dostepu managerskiego


        System.out.println("Usuwanie  flat "+flatId);


        flatService.removeFlat(flatId);
        return ResponseEntity.ok().build();
    }
}
