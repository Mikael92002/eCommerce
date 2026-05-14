package com.mikael.eCommerce.TestEndpoints;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/welcome")
    public String allAccess(){
        return "Everyone access";
    }

    @GetMapping("/user")
    public String userAccessOnly(){
        return "Only users and admins w/ jwt can access";
    }

    @GetMapping("/admin")
    public String adminAccessOnly(){
        return "Only admins w/ jwt can access";
    }
}