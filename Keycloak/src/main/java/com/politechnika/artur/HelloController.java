package com.politechnika.artur;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.IDToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HelloController {
    private final AccessToken accessToken;
    private final KeycloakSecurityContext securityContext;

    @GetMapping("/public")
    public String hello(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/user")
    @RolesAllowed("USER")
    public String isUser() {
        return "USER";
    }

    @GetMapping("/admin")
    @RolesAllowed("ADMIN")
    public String isAdmin() {
        return "ADMIN";
    }

    @GetMapping("/moderator")
    @RolesAllowed("MANAGER")
    public String isModerator() {
        return "MODERATOR";
    }

    @GetMapping("/info")
    public String info(Principal principal) {
        return "Jestem zalogowany jako: " + principal.getName();
    }

    @GetMapping("/username")
    public String getUsername() {
        return accessToken.getPreferredUsername();
    }

    @GetMapping(value = "/usernameKeycloak")
    public String getUserNameForKeycloak(Principal principal)
    {
        return principal.getName();
    }

}
