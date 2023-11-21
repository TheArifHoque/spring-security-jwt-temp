package com.arifhoque.main.controller;

import com.arifhoque.main.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {


    @PostMapping("/login")
    public String loginPage(@RequestBody User user) {
        return null;
    }

    @PostMapping("/user/register")
    public String userRegistration() {
        return "OK";
    }

    @GetMapping("/admin/register")
    public String adminRegistrationPage() {
        return "OK";
    }

    @GetMapping("/user/dashboard")
    public String userDashboard() {
        return "welcome to user dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "welcome to admin dashboard";
    }
}
