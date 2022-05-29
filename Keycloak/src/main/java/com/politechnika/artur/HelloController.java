package com.politechnika.artur;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

@RestController
public class HelloController {

    @GetMapping("/public")
    public String hello() {
        return "Jestem publiczny!";
    }

    @GetMapping("/user")
    @RolesAllowed("USER")
    public String user() {
        return "Jestem user!";
    }

    @GetMapping("/admin")
    @RolesAllowed("ADMIN")
    public String admin() {
        return "Jestem admin!";
    }

    @GetMapping("/info")
    @RolesAllowed({"ADMIN", "USER"})
    public String info(Principal principal) {
        return "Jestem zalogowany jako: " + principal.getName();
    }
}
