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
    public String isUser(Principal principal) {
        return "USER";
    }

    @GetMapping("/admin")
    @RolesAllowed("ADMIN")
    public String isAdmin(Principal principal) {
        return "ADMIN";
    }

    @GetMapping("/moderator")
    @RolesAllowed("MODERATOR")
    public String isModerator(Principal principal) {
        return "MODERATOR";
    }

    @GetMapping("/info")
    public String info(Principal principal) {
        return "Jestem zalogowany jako: " + principal.getName();
    }

    @GetMapping("/username")
    public String getUsername(Principal principal) {
        return principal.getName();
    }

}
