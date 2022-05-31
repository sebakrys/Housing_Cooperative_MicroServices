package com.nsai.spoldzielnia.Service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AuthService {

    public  String keycloakAuthAddr = "http://localhost:8091";

    public Boolean isAdmin(String JWT_Token){
        //weryfikacja czy jest admin
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", JWT_Token);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity re= new RestTemplate().exchange(keycloakAuthAddr+"/admin", HttpMethod.GET, request, String.class);
        System.out.println(re.getStatusCodeValue());
        if(re.getStatusCodeValue()!=200){//jesli nie 200 wycofaj
            return false;
        }else{
            return true;
        }
    }

    public Boolean isManager(String JWT_Token){
        //weryfikacja czy jest manager
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", JWT_Token);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity re= new RestTemplate().exchange(keycloakAuthAddr+"/moderator", HttpMethod.GET, request, String.class);
        System.out.println(re.getStatusCodeValue());
        if(re.getStatusCodeValue()!=200){//jesli nie 200 wycofaj
            return false;
        }else{
            return true;
        }
    }

    public Boolean isUser(String JWT_Token){
        //weryfikacja czy jest user
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", JWT_Token);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity re= new RestTemplate().exchange(keycloakAuthAddr+"/user", HttpMethod.GET, request, String.class);
        System.out.println(re.getStatusCodeValue());
        if(re.getStatusCodeValue()!=200){//jesli nie 200 wycofaj
            return false;
        }else{
            return true;
        }
    }

    public String getUsername(String JWT_Token){
        //weryfikacja czy jest user
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", JWT_Token);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity re= new RestTemplate().exchange(keycloakAuthAddr+"/username", HttpMethod.GET, request, String.class);
        System.out.println(re.getStatusCodeValue());
        if(re.getStatusCodeValue()!=200){//jesli nie 200 wycofaj
            return null;
        }else{
            return re.getBody().toString();
        }
    }

    public Long getUserID(String JWT_Token){
        String username = this.getUsername(JWT_Token);
        if(username!=null){
            ResponseEntity re= new RestTemplate().exchange("http://localhost:8000/residents-flat-service/getID/"+username, HttpMethod.GET, HttpEntity.EMPTY, String.class);
            System.out.println(re.getStatusCodeValue());
            if(re.getStatusCodeValue()!=200){
                return -1l;//jesli nie 200 wycofaj
            }else {
                long id = Long.valueOf(re.getBody().toString());
                return id;
            }
        }
        return -1l;

    }

    public Boolean isLocatorFlat(long locator_id, long flat_id){

        //Boolean lf = new RestTemplate().getForObject("http://localhost:8000/managers-locators-service/getLocatorFromFlat/"+flat_id+"/"+locator_id, Boolean.class);
        ResponseEntity re= new RestTemplate().exchange("http://localhost:8000/managers-locators-service/getLocatorFromFlat/"+flat_id+"/"+locator_id, HttpMethod.GET, HttpEntity.EMPTY, Boolean.class);
        System.out.println(re.getStatusCodeValue());
        if(re.getStatusCodeValue()!=200){
            return false;//jesli nie 200 wycofaj
        }else {
            Boolean lf = (boolean) re.getBody();
            System.out.println(lf);
            return true;
        }
    }

    public Boolean isManagerBuilding(long manager_id, long building_id){

        //Boolean lf = new RestTemplate().getForObject("http://localhost:8000/managers-locators-service/getLocatorFromFlat/"+flat_id+"/"+locator_id, Boolean.class);
        ResponseEntity re= new RestTemplate().exchange("http://localhost:8000/managers-locators-service/getManagerFromBuilding/"+building_id+"/"+manager_id, HttpMethod.GET, HttpEntity.EMPTY, Boolean.class);
        System.out.println(re.getStatusCodeValue());
        if(re.getStatusCodeValue()!=200){
            return false;//jesli nie 200 wycofaj
        }else {
            Boolean mb = (boolean) re.getBody();
            System.out.println(mb);
            return true;
        }
    }

    public ResponseEntity<String> nExchange(String url, String JWT_Token){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", JWT_Token);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        return new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);
        //return new RestTemplate().exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);
    }

    public List<Integer> nGetForObjectListInteger(String url, String JWT_Token){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", JWT_Token);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        return new RestTemplate().exchange(url, HttpMethod.GET, request, List.class).getBody();
        //return new RestTemplate().getForObject(url, String.class).;
        //return new RestTemplate().exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);
    }

    public String nGetForObjectString(String url, String JWT_Token){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", JWT_Token);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        return new RestTemplate().exchange(url, HttpMethod.GET, request, String.class).getBody();
        //return new RestTemplate().getForObject(url, String.class).;
        //return new RestTemplate().exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);
    }

    public Long nGetForObjectLong(String url, String JWT_Token){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", JWT_Token);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        return new RestTemplate().exchange(url, HttpMethod.GET, request, Long.class).getBody();
        //return new RestTemplate().getForObject(url, String.class).;
        //return new RestTemplate().exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);
    }



}
