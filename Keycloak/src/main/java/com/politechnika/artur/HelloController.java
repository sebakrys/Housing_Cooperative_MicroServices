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
    public String user(Principal principal) {
        return "Jestem zalogowany jako user: " + principal.getName();
    }

    @GetMapping("/admin")
    @RolesAllowed("ADMIN")
    public String admin(Principal principal) {
        return "Jestem zalogowany jako admin: " + principal.getName();
    }

    @GetMapping("/moderator")
    @RolesAllowed("MODERATOR")
    public String moderator(Principal principal) {
        return "Jestem zalogowany jako moderator: " + principal.getName();
    }

    @GetMapping("/info")
    public String info(Principal principal) {
        return "Jestem zalogowany jako: " + principal.getName();
    }


}
