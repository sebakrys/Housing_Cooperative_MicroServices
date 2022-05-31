package com.nsai.spoldzielnia.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsai.spoldzielnia.Entity.Building;
import com.nsai.spoldzielnia.Entity.Flat;
import com.nsai.spoldzielnia.Entity.FlatCharges;
import com.nsai.spoldzielnia.Rabbit.Notification;
import com.nsai.spoldzielnia.Repository.FlatChargesRepository;
import com.nsai.spoldzielnia.Service.AuthService;
import com.nsai.spoldzielnia.Service.BuildingService;
import com.nsai.spoldzielnia.Service.FlatChargesService;
import com.nsai.spoldzielnia.Service.FlatService;
import com.nsai.spoldzielnia.Validator.BuildingValidator;
import com.nsai.spoldzielnia.Validator.FlatChargesValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class FlatChargesController {

    private static final String QUEUE_244019 = "244019";
    private final RabbitTemplate rabbitTemplate;

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
    @Autowired
    private BuildingService buildingService;

    @Autowired
    private AuthService authService;

    private final FlatChargesRepository flatChargesRepository;

    private FlatChargesValidator flatChargesValidator = new FlatChargesValidator();
    private RestTemplate restTemplate = new RestTemplate();

    public FlatChargesController(RabbitTemplate rabbitTemplate, FlatChargesRepository flatChargesRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.flatChargesRepository = flatChargesRepository;
    }


    //POST
    @PostMapping(value = "/addNewFlatCharges")//mieszkaniec nie moze ustawiac na true accepted i zaplacone(nie moze zmienaic tego)
    public ResponseEntity<FlatCharges> addNewFlatCharges(@RequestBody @Valid FlatCharges flatCharges, BindingResult result, @RequestHeader (name="Authorization") String token) throws JsonProcessingException {
        System.out.println(flatCharges.toString());
        Flat tmpFlat = flatService.getFlat(flatCharges.getFlat().getId());


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
                    boolean isLocatorFlat = authService.isLocatorFlat(locator_id, flatCharges.getFlat().getId());
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


        flatCharges.setFlat(tmpFlat);
        System.out.println(flatCharges.toString());

        flatChargesValidator.validate(flatCharges, result);

        if(flatCharges.getFlat()==null){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }

        if (result.getErrorCount() == 0) {
            //dodawanie budynku
            System.out.println("dodawanie flatcharges");
            if(flatCharges.isAccepted() && !managAccess)flatCharges.setAccepted(false);// jesli nie ma dostepu managerskiego
            if(flatCharges.isZaplacone() && !managAccess)flatCharges.setZaplacone(false);// jesli nie ma dostepu managerskiego

            if(flatCharges.isAccepted()){
                //wyslij maila do mieszkanców o zaplate;
                try {
                    //getLocators
                    //pobieranie listy id uzytkownikow(Locators) z mieszkania
                    List<Integer> locatorIdList =  authService.nGetForObjectListInteger("http://localhost:8000/managers-locators-service/getAllLocatorsFromFlat/"+flatCharges.getFlat().getId(), token);
                    Map<Integer, String> usrNames = new HashMap<>();
                    for (Integer usrId: locatorIdList) usrNames.put(usrId, authService.nGetForObjectString("http://localhost:8000/residents-flat-service/getNames/"+usrId, token));
                    for (Integer id: locatorIdList) {
                        System.out.println(id);
                        //wysylanie notyfikacji
                        String otherLocators = "";
                        for (Integer key : usrNames.keySet()) {
                            if(key!=id)otherLocators=otherLocators+","+usrNames.get(key);
                        }

                        String UrlVariabledPDF = "year="+flatCharges.getData().getYear()
                                +"&month="+flatCharges.getData().getMonth().getValue()+
                                "&ryczalt="+false
                                +"&flatid="+tmpFlat.getId()+"&buildingid="+tmpFlat.getBuilding().getId()+"&street="+tmpFlat.getBuilding().getStreet()+"&bNr="+tmpFlat.getBuilding().getBuildingNumber()+"&fNr="+tmpFlat.getFlatNumber()+"&postalcode="+tmpFlat.getBuilding().getPostalCode()+"&city="+tmpFlat.getBuilding().getCity()
                                +"&fr="+flatCharges.getFunduszRemontowy()+"&fr_rate="+flatCharges.getFunduszRemontowy_stawka()
                                +"&g="+flatCharges.getGaz()+"&g_rate="+flatCharges.getGaz_stawka()
                                +"&og="+flatCharges.getOgrzewanie()+"&og_rate="+flatCharges.getOgrzewanie_stawka()
                                +"&pr="+flatCharges.getPrad()+"&pr_rate="+flatCharges.getPrad_stawka()
                                +"&sc="+flatCharges.getScieki()+"&sc_rate="+flatCharges.getScieki_stawka()
                                +"&cw="+flatCharges.getWoda_ciepla()+"&cw_rate="+flatCharges.getWoda_ciepla_stawka()
                                +"&zw="+flatCharges.getWoda_zimna()+"&zw_rate="+flatCharges.getWoda_zimna_stawka()
                                +"&usr_names="+usrNames.get(id)+"&otherLocators="+otherLocators+"&ch=";

                        UrlVariabledPDF = UrlVariabledPDF.replaceAll(" ", "%20");
                        long checksum =  flatChargesService.generateChecksum(UrlVariabledPDF);
                        //System.out.println(UrlVariabledPDF);
                        //System.out.println(checksum);
                        UrlVariabledPDF = UrlVariabledPDF+checksum;

                        String rachunekPdfUrl = "http://localhost:8090/rachunek?";
                        String tmlUsrEmail = authService.nGetForObjectString("http://localhost:8000/residents-flat-service/getEmail/"+id, token);
                        Notification tmpNoti = new Notification(tmlUsrEmail, "Odczyty zaakceptowane", "Odczyty z "+flatCharges.getData().getMonth().getValue()+" "+flatCharges.getData().getYear()+" zostały zaakceptowane. Rachunek wystawiony pod linkiem: "+rachunekPdfUrl+UrlVariabledPDF);
                        System.out.println(tmpNoti.toString());
                        rabbitTemplate.convertAndSend(QUEUE_244019, tmpNoti);
                    }
                }
                catch (final HttpClientErrorException e) {
                    System.out.println(e.getStatusCode());
                    System.out.println(e.getResponseBodyAsString());
                }

            }
            if(flatCharges.isZaplacone()){
                //wyslij maila do mieszkanców i managerow ze jest zaplacone;
                try {
                    //getLocators
                    //pobieranie listy id uzytkownikow(Locators) z mieszkania
                    List<Integer> locatorIdList = authService.nGetForObjectListInteger("http://localhost:8000/managers-locators-service/getAllLocatorsFromFlat/" + flatCharges.getFlat().getId(), token);
                    for (Integer id: locatorIdList) {
                        System.out.println(id);
                        //wysylanie notyfikacji o zaplaceniu
                        rabbitTemplate.convertAndSend(QUEUE_244019, new Notification("email", "Opłaty uregulowane", "Opłaty za "+flatCharges.getData().getMonth().getValue()+" "+flatCharges.getData().getYear()+" zostały uregulowane"));
                    }

                    //getManagers
                    //pobieranie listy id uzytkownikow(Managers) z budynku
                    List<Integer> managersIdList = authService.nGetForObjectListInteger("http://localhost:8000/managers-locators-service/getAllManagersFromBuilding/" + flatCharges.getFlat().getBuilding().getId(), token);
                    for (Integer id: managersIdList) {
                        System.out.println(id);
                        //wysylanie notyfikacji o zaplaceniu Managerom
                        rabbitTemplate.convertAndSend(QUEUE_244019, new Notification("email", "Opłaty uregulowane", "Opłaty za mieszkanie ul. "+tmpFlat.getBuilding().getStreet()+" nr. "+tmpFlat.getBuilding().getBuildingNumber()+"/"+tmpFlat.getFlatNumber()+" za okres: "+flatCharges.getData().getMonth().getValue()+" "+flatCharges.getData().getYear()+" zostały uregulowane"));
                    }

                }
                catch (final HttpClientErrorException e) {
                    System.out.println(e.getStatusCode());
                    System.out.println(e.getResponseBodyAsString());
                }
            }
            return ResponseEntity.ok(flatChargesService.addFlatCharges(flatCharges));
        }

        System.out.println("są bledy validatora");


        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    //PUT
    @PutMapping("/updateFlatCharges")//mieszkaniec nie moze ustawiac na true accepted i zaplacone(nie moze zmienaic tego)
    public ResponseEntity<FlatCharges> updateFlatCharges(@RequestBody FlatCharges flatCharges, BindingResult result, @RequestHeader (name="Authorization") String token) throws JsonProcessingException {

        FlatCharges tmpFlatCharges = flatChargesService.getFlatCharges(flatCharges.getId());
        if(tmpFlatCharges==null)return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

        Flat tmpFlat = flatService.getFlat(flatCharges.getFlat().getId());


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
                    boolean isLocatorFlat = authService.isLocatorFlat(locator_id, flatCharges.getFlat().getId());
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



        flatCharges.setFlat(tmpFlat);
        System.out.println(new ObjectMapper().writeValueAsString(flatCharges));

        flatChargesValidator.validate(flatCharges, result);


        if (result.getErrorCount() == 0) {
            System.out.println("edit flatCharges");
            if(!tmpFlatCharges.isAccepted() && flatCharges.isAccepted() && !managAccess) flatCharges.setAccepted(false);// nie moze zaakceptowac jesli nie ma dostepu managerskiego
            if(!tmpFlatCharges.isZaplacone() && flatCharges.isZaplacone() && !managAccess)flatCharges.setZaplacone(false);// nie moze zaakceptowac platnosci jesli nie ma dostepu managerskiego


            if(!tmpFlatCharges.isAccepted() && flatCharges.isAccepted()){
                //wyslij maila do mieszkanców o zaplate;
                try {
                    //getLocators
                    //pobieranie listy id uzytkownikow(Locators) z mieszkania
                    List<Integer> locatorIdList =  authService.nGetForObjectListInteger("http://localhost:8000/managers-locators-service/getAllLocatorsFromFlat/"+flatCharges.getFlat().getId(), token);
                    Map<Integer, String> usrNames = new HashMap<>();
                    for (Integer usrId: locatorIdList) usrNames.put(usrId, authService.nGetForObjectString("http://localhost:8000/residents-flat-service/getNames/"+usrId, token));
                    for (Integer id: locatorIdList) {
                        System.out.println(id);
                        //wysylanie notyfikacji
                        String otherLocators = "";
                        for (Integer key : usrNames.keySet()) {
                            if(key!=id)otherLocators=otherLocators+","+usrNames.get(key);
                        }

                        String UrlVariabledPDF = "year="+flatCharges.getData().getYear()
                                +"&month="+flatCharges.getData().getMonth().getValue()+
                                "&ryczalt="+false
                                +"&flatid="+tmpFlat.getId()+"&buildingid="+tmpFlat.getBuilding().getId()+"&street="+tmpFlat.getBuilding().getStreet()+"&bNr="+tmpFlat.getBuilding().getBuildingNumber()+"&fNr="+tmpFlat.getFlatNumber()+"&postalcode="+tmpFlat.getBuilding().getPostalCode()+"&city="+tmpFlat.getBuilding().getCity()
                                +"&fr="+flatCharges.getFunduszRemontowy()+"&fr_rate="+flatCharges.getFunduszRemontowy_stawka()
                                +"&g="+flatCharges.getGaz()+"&g_rate="+flatCharges.getGaz_stawka()
                                +"&og="+flatCharges.getOgrzewanie()+"&og_rate="+flatCharges.getOgrzewanie_stawka()
                                +"&pr="+flatCharges.getPrad()+"&pr_rate="+flatCharges.getPrad_stawka()
                                +"&sc="+flatCharges.getScieki()+"&sc_rate="+flatCharges.getScieki_stawka()
                                +"&cw="+flatCharges.getWoda_ciepla()+"&cw_rate="+flatCharges.getWoda_ciepla_stawka()
                                +"&zw="+flatCharges.getWoda_zimna()+"&zw_rate="+flatCharges.getWoda_zimna_stawka()
                                +"&usr_names="+usrNames.get(id)+"&otherLocators="+otherLocators+"&ch=";

                        UrlVariabledPDF = UrlVariabledPDF.replaceAll(" ", "%20");
                        long checksum =  flatChargesService.generateChecksum(UrlVariabledPDF);
                        //System.out.println(UrlVariabledPDF);
                        //System.out.println(checksum);
                        UrlVariabledPDF = UrlVariabledPDF+checksum;

                        String rachunekPdfUrl = "http://localhost:8090/rachunek?";
                        String tmlUsrEmail = authService.nGetForObjectString("http://localhost:8000/residents-flat-service/getEmail/"+id, token);
                        Notification tmpNoti = new Notification(tmlUsrEmail, "Odczyty zaakceptowane", "Odczyty z "+flatCharges.getData().getMonth().getValue()+" "+flatCharges.getData().getYear()+" zostały zaakceptowane. Rachunek wystawiony pod linkiem: "+rachunekPdfUrl+UrlVariabledPDF);
                        System.out.println(tmpNoti.toString());
                        rabbitTemplate.convertAndSend(QUEUE_244019, tmpNoti);
                    }
                }
                catch (final HttpClientErrorException e) {
                    System.out.println(e.getStatusCode());
                    System.out.println(e.getResponseBodyAsString());
                }
            }
            if(!tmpFlatCharges.isZaplacone() && flatCharges.isZaplacone()){
                //wyslij maila do mieszkanców i managerow ze jest zaplacone;
                try {
                    //getLocators
                    //pobieranie listy id uzytkownikow(Locators) z mieszkania
                    List<Integer> locatorIdList = authService.nGetForObjectListInteger("http://localhost:8000/managers-locators-service/getAllLocatorsFromFlat/" + flatCharges.getFlat().getId(), token);
                    for (Integer id: locatorIdList) {
                        System.out.println(id);
                        //wysylanie notyfikacji o zaplaceniu
                        rabbitTemplate.convertAndSend(QUEUE_244019, new Notification("email", "Opłaty uregulowane", "Opłaty za "+flatCharges.getData().getMonth().getValue()+" "+flatCharges.getData().getYear()+" zostały uregulowane"));
                    }

                    //getManagers
                    //pobieranie listy id uzytkownikow(Managers) z budynku
                    List<Integer> managersIdList = authService.nGetForObjectListInteger("http://localhost:8000/managers-locators-service/getAllManagersFromBuilding/" + flatCharges.getFlat().getBuilding().getId(), token);
                    for (Integer id: managersIdList) {
                        System.out.println(id);
                        //wysylanie notyfikacji o zaplaceniu Managerom
                        rabbitTemplate.convertAndSend(QUEUE_244019, new Notification("email", "Opłaty uregulowane", "Opłaty za mieszkanie ul. "+tmpFlat.getBuilding().getStreet()+" nr. "+tmpFlat.getBuilding().getBuildingNumber()+"/"+tmpFlat.getFlatNumber()+" za okres: "+flatCharges.getData().getMonth().getValue()+" "+flatCharges.getData().getYear()+" zostały uregulowane"));
                    }

                }
                catch (final HttpClientErrorException e) {
                    System.out.println(e.getStatusCode());
                    System.out.println(e.getResponseBodyAsString());
                }
            }

            return ResponseEntity.ok(flatChargesService.editFlatCharges(flatCharges));
        }


        System.out.println("są bledy validatora");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    @GetMapping("/getAllFlatCharges")
    public ResponseEntity<List<FlatCharges>> getAllFlatCharges(@RequestHeader (name="Authorization") String token){
        //aaa weryfikacja czy jest admin
        boolean admin = authService.isAdmin(token);
        if(!admin) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(flatChargesService.listFlatCharges());
    }

    @GetMapping("/getFlatCharges/{id}")
    public ResponseEntity<FlatCharges> getFlatChargesById(@PathVariable Long id, @RequestHeader (name="Authorization") String token){

        Optional<FlatCharges> optFlatCharges = flatChargesRepository.findById(id);
        FlatCharges tmpFlatCharges = optFlatCharges.get();
        Flat tmpFlat = flatService.getFlat(tmpFlatCharges.getFlat().getId());
        //Building tmpBuilding = buildingService.getBuilding(tmpFlat.getBuilding().getId());


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
                    boolean isLocatorFlat = authService.isLocatorFlat(locator_id, tmpFlatCharges.getFlat().getId());
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

        return optFlatCharges
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }


    //DELETE
    @DeleteMapping("/deleteFlatCharges/{flatChargesId}")
    public ResponseEntity<Building> deleteFlatCharges(@PathVariable Long flatChargesId, @RequestHeader (name="Authorization") String token) {


        FlatCharges tmpFlatCharges = flatChargesService.getFlatCharges(flatChargesId);
        if(tmpFlatCharges==null)return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        Flat tmpFlat = flatService.getFlat(tmpFlatCharges.getFlat().getId());
        Building tmpBuilding = buildingService.getBuilding(tmpFlat.getBuilding().getId());




        //aaa weryfikacja czy jest admin lub manager lub locator tego mieszkania
        boolean admin = authService.isAdmin(token);
        boolean managAccess = false;
        //boolean locatorAccess = false;
        if(admin) managAccess = true;//jest adminem
        else {
            boolean manager = authService.isManager(token);
            //boolean locator = authService.isUser(token);
            long user_id = authService.getUserID(token);
            /*if(locator){//jest locatorerm
                long locator_id = user_id;
                if(locator_id!=-1l) {
                    boolean isLocatorFlat = authService.isLocatorFlat(locator_id, tmpFlatCharges.getFlat().getId());
                    if(isLocatorFlat) locatorAccess = true;//jest locatorem tego mieszkania
                }
            }*/
            if(manager){//jesli jest managerem
                long manager_id = user_id;
                if(manager_id!=-1l) {
                    boolean isManagBuild = authService.isManagerBuilding(manager_id, tmpFlat.getBuilding().getId());
                    if(isManagBuild) managAccess = true;//jest managerem budynku tego mieszkania
                }
            }
        }
        if(!managAccess)return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//jesli nie ma dostepu managerskiego



        System.out.println("Usuwanie  flatCharges "+flatChargesId);


        flatChargesService.removeFlatCharges(flatChargesId);
        return ResponseEntity.ok().build();
    }


}
