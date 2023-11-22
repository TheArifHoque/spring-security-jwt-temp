package com.arifhoque.main.controller;

import com.arifhoque.main.model.User;
import com.arifhoque.main.service.UserService;
import com.arifhoque.main.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String loginPage(@RequestBody User user) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        } catch (Exception ex) {
            return ex.toString();
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtil.generateToken(userDetails);
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
