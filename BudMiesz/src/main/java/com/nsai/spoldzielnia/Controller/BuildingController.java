package com.nsai.spoldzielnia.Controller;

import com.nsai.spoldzielnia.Entity.Building;
import com.nsai.spoldzielnia.Entity.Flat;
import com.nsai.spoldzielnia.Repository.BuildingRepository;
import com.nsai.spoldzielnia.Service.AuthService;
import com.nsai.spoldzielnia.Service.BuildingService;
import com.nsai.spoldzielnia.Service.FlatService;
import com.nsai.spoldzielnia.Validator.BuildingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")//for React
@RestController
public class    BuildingController {

    @Autowired
    private AuthService authService;

    @Autowired
    private BuildingService buildingService;

    private final BuildingRepository buildingRepository;

    @Autowired
    private FlatService flatService;


    private BuildingValidator buildingValidator = new BuildingValidator();

    public BuildingController(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public  String keycloakAuthAddr = "http://localhost:8091";

    //POST
    @PostMapping(value = "/addNewBuilding")
    public ResponseEntity<Building> addNewBuilding(@RequestBody Building building, BindingResult result, @RequestHeader (name="Authorization") String token) {

        //todo weryfikacja czy jest admin
        boolean admin = authService.isAdmin(token);
        if(!admin) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        /*
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity re= new RestTemplate().exchange(keycloakAuthAddr+"/admin", HttpMethod.GET, request, String.class);
        System.out.println(re.getStatusCodeValue());
        if(re.getStatusCodeValue()!=200)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();;//jesli nie 200 wycofaj
         */



        System.out.println(building.getNazwa()+" "+building.getId()+" "+building.getCity());
        buildingValidator.validate(building, result);


        if (result.getErrorCount() == 0) {
            //dodawanie budynku
            System.out.println("dodawanie budynku");
            return ResponseEntity.ok(buildingService.addBuilding(building));
        }

        System.out.println("są bledy validatora");


        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    //PUT
    @PutMapping("/updateBuilding")
    public ResponseEntity<Building> updateBuilding(@RequestBody Building building, BindingResult result)
    {
        if(buildingService.getBuilding(building.getId())==null)return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

        buildingValidator.validate(building, result);

        if (result.getErrorCount() == 0) {
            System.out.println("edit budynku");
            return ResponseEntity.ok(buildingService.editBuilding(building));
        }


        System.out.println("są bledy validatora");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    @GetMapping("/getBuildings")
    public List<Building> getAllBuildings(){
        return buildingService.listBuildings();
    }

    @GetMapping("/getBuilding/{id}")
    public ResponseEntity<Building> getBuildingById(@PathVariable Long id){
        return buildingRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping("/getBuildingFlats/{id}")
    public ResponseEntity<List<Flat>> getBuildingFlatsById(@PathVariable Long id){
        Building tmpBuilding = buildingService.getBuilding(id);
        if(tmpBuilding==null)return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

        return ResponseEntity.ok(tmpBuilding.getFlat());
    }


    //DELETE
    @DeleteMapping("/deleteBuilding/{buildingId}")
    public ResponseEntity<Building> deleteBuilding(@PathVariable Long buildingId) {

        System.out.println("Usuwanie  budynku "+buildingId);

        if(buildingService.getBuilding(buildingId)==null)return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();


        buildingService.removeBuilding(buildingId);
        return ResponseEntity.ok().build();
    }




}
