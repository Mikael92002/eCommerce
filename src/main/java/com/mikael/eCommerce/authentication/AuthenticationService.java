package com.mikael.eCommerce.authentication;

import com.mikael.eCommerce.config.JwtUtils;
import com.mikael.eCommerce.users.DTOs.UserRegistrationDTO;
import com.mikael.eCommerce.users.DTOs.UserRequestDTO;
import com.mikael.eCommerce.users.DTOs.UserResponseDTO;
import com.mikael.eCommerce.users.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {



    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                    UserService userService,
                                    PasswordEncoder passwordEncoder,
                                    JwtUtils jwtUtils){
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public String authenticateUser(UserRequestDTO userRequestDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userRequestDTO.username(), userRequestDTO.password())
        );

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return jwtUtils.generateToken(userDetails);
    }

    public UserResponseDTO registerUser(UserRegistrationDTO userRegistrationDTO){
        return this.userService.createUser(userRegistrationDTO);
    }

}
