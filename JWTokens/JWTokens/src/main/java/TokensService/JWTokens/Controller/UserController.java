package TokensService.JWTokens.Controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {

    User user = new User();
    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    private final String SECRET = "mySecretKey";

    @PostMapping("login")
    public User login(@RequestParam("user") String username, @RequestParam("password") String pwd,  HttpServletResponse response) throws IOException {

        String token = getJWTToken(username);
        user.setUsername(username);
        user.setToken(token);
        response.addHeader("User",username);
        response.addHeader("password", pwd);
        response.sendRedirect("http://localhost:8000/jwt-service/name");
        return user;

    }

        @RequestMapping(value = "name", method = RequestMethod.GET)
        public String getName(Authentication authentication, Principal principal, HttpServletRequest request, HttpServletResponse response) {

            System.out.println(request.getHeader("Authorization"));
            String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
            Claims dupa = Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
            System.out.println(dupa.get("email"));
            return "redirect:/checkPerson";
        }

    private String getJWTToken(String username) {

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("JWT")
                .setSubject(username)
                .claim("email","dlaldasldlas@gmail.com")
                .claim("USER", new User("DAJ MNIE", "TO"))
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        SECRET.getBytes()).compact();

        return "Bearer " + token;
    }
}