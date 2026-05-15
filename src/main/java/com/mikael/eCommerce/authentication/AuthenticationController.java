package com.mikael.eCommerce.authentication;

import com.mikael.eCommerce.users.DTOs.UserRegistrationDTO;
import com.mikael.eCommerce.users.DTOs.UserRequestDTO;
import com.mikael.eCommerce.users.DTOs.UserResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Value("${jwt.cookieName}")
    private String cookieName;

    @Value("${jwt.cookieExpiration-s}")
    private long cookieExpiration;

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signin")
    public ResponseEntity<Void> authenticateUser(@RequestBody UserRequestDTO user, HttpServletResponse response){
        String token = this.authenticationService.authenticateUser(user);

        ResponseCookie cookie = ResponseCookie.from(cookieName, token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(cookieExpiration)
                .sameSite(SameSiteCookies.STRICT.toString())
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    public UserResponseDTO registerUser(@Valid @RequestBody UserRegistrationDTO user){
        return this.authenticationService.registerUser(user);
    }

    @PostMapping("/signout")
    public ResponseEntity<Object> signOutUser(HttpServletResponse response){
        ResponseCookie cookie = ResponseCookie.from(cookieName, "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite(SameSiteCookies.STRICT.toString())
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok().build();
    }
}
