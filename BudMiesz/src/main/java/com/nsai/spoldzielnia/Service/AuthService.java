package com.nsai.spoldzielnia.Service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AuthService {

    public  String keycloakAuthAddr = "http://localhost:8000/keycloak-service";

    public Boolean isAdmin(String JWT_Token){
        //weryfikacja czy jest admin
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", JWT_Token);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        try {
            ResponseEntity re= new RestTemplate().exchange(keycloakAuthAddr+"/admin", HttpMethod.GET, request, String.class);
            System.out.println(re.getStatusCodeValue());
            if(re.getStatusCodeValue()!=200){//jesli nie 200 wycofaj
                return false;
            }else{
                return true;
            }
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public Boolean isManager(String JWT_Token){
        //weryfikacja czy jest manager
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", JWT_Token);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        try {
            ResponseEntity re= new RestTemplate().exchange(keycloakAuthAddr+"/moderator", HttpMethod.GET, request, String.class);
            System.out.println(re.getStatusCodeValue());
            if(re.getStatusCodeValue()!=200){//jesli nie 200 wycofaj
                return false;
            }else{
                return true;
            }
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public Boolean isUser(String JWT_Token){
        //weryfikacja czy jest user
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", JWT_Token);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        try {
            ResponseEntity re= new RestTemplate().exchange(keycloakAuthAddr+"/user", HttpMethod.GET, request, String.class);
            System.out.println(re.getStatusCodeValue());
            if(re.getStatusCodeValue()!=200){//jesli nie 200 wycofaj
                return false;
            }else{
                return true;
            }
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public String getUsername(String JWT_Token){
        //weryfikacja czy jest user
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", JWT_Token);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        try {
            ResponseEntity re= new RestTemplate().exchange(keycloakAuthAddr+"/username", HttpMethod.GET, request, String.class);
            System.out.println(re.getStatusCodeValue());
            if(re.getStatusCodeValue()!=200){//jesli nie 200 wycofaj
                return null;
            }else{
                return re.getBody().toString();
            }
        }catch (Exception e){
            System.out.println(e);
            return null;
        }

    }

    public Long getUserID(String JWT_Token){
        String username = this.getUsername(JWT_Token);
        if(username!=null){
            try {
                ResponseEntity re= new RestTemplate().exchange("http://localhost:8000/residents-flat-service/getID/"+username, HttpMethod.GET, HttpEntity.EMPTY, String.class);
                System.out.println(re.getStatusCodeValue());
                if(re.getStatusCodeValue()!=200){
                    return -1l;//jesli nie 200 wycofaj
                }else {
                    long id = Long.valueOf(re.getBody().toString());
                    return id;
                }
            }catch (Exception e){
                System.out.println(e);
                return -1l;
            }
        }
        return -1l;

    }

    public Boolean isLocatorFlat(long locator_id, long flat_id, String JWT_Token){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", JWT_Token);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        //Boolean lf = new RestTemplate().getForObject("http://localhost:8000/managers-locators-service/getLocatorFromFlat/"+flat_id+"/"+locator_id, Boolean.class);
        try {
            ResponseEntity re= new RestTemplate().exchange("http://localhost:8000/managers-locators-service/getLocatorFromFlat/"+flat_id+"/"+locator_id, HttpMethod.GET, request, Boolean.class);
            System.out.println(re.getStatusCodeValue());
            if(re.getStatusCodeValue()!=200){
                return false;//jesli nie 200 wycofaj
            }else {
                Boolean lf = (boolean) re.getBody();
                System.out.println(lf);
                return true;
            }
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public Boolean isManagerBuilding(long manager_id, long building_id, String JWT_Token){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", JWT_Token);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        //Boolean lf = new RestTemplate().getForObject("http://localhost:8000/managers-locators-service/getLocatorFromFlat/"+flat_id+"/"+locator_id, Boolean.class);
        try {
            ResponseEntity re= new RestTemplate().exchange("http://localhost:8000/managers-locators-service/getManagerFromBuilding/"+building_id+"/"+manager_id, HttpMethod.GET, request, Boolean.class);
            System.out.println(re.getStatusCodeValue());
            if(re.getStatusCodeValue()!=200){
                return false;//jesli nie 200 wycofaj
            }else {
                Boolean mb = (boolean) re.getBody();
                System.out.println(mb);
                return true;
            }
        }catch (Exception e){
            System.out.println(e);
            return false;
        }

    }

    public ResponseEntity<String> nExchange(String url, String JWT_Token){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", JWT_Token);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        try {
            ResponseEntity<String> lreq = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);
            return lreq;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
        //return new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);
        //return new RestTemplate().exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);
    }

    public List<Integer> nGetForObjectListInteger(String url, String JWT_Token){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", JWT_Token);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        try {
            List<Integer> lreq = new RestTemplate().exchange(url, HttpMethod.GET, request, List.class).getBody();
            return lreq;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
        //return new RestTemplate().exchange(url, HttpMethod.GET, request, List.class).getBody();
        //return new RestTemplate().getForObject(url, String.class).;
        //return new RestTemplate().exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);
    }

    public String nGetForObjectString(String url, String JWT_Token){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", JWT_Token);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        try {
            String lreq = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class).getBody();
            return lreq;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
        //return new RestTemplate().exchange(url, HttpMethod.GET, request, String.class).getBody();
        //return new RestTemplate().getForObject(url, String.class).;
        //return new RestTemplate().exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);
    }

    public Long nGetForObjectLong(String url, String JWT_Token){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", JWT_Token);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        try {
            Long lreq = new RestTemplate().exchange(url, HttpMethod.GET, request, Long.class).getBody();
            return lreq;
        }catch (Exception e){
            System.out.println(e);
            return -1l;
        }
        //return new RestTemplate().exchange(url, HttpMethod.GET, request, Long.class).getBody();
        //return new RestTemplate().getForObject(url, String.class).;
        //return new RestTemplate().exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);
    }



}
