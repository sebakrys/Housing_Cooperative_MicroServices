package com.nsai.spoldzielnia.Service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
}
