package com.arifhoque.main.controller;

import com.arifhoque.main.model.JwtResponse;
import com.arifhoque.main.model.User;
import com.arifhoque.main.service.UserService;
import com.arifhoque.main.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUsername(),user.getPassword()
            ));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>("Incorrect credentials!", HttpStatus.BAD_REQUEST);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails);
        JwtResponse jwtResponse = JwtResponse.builder()
                .type("Bearer")
                .username(user.getUsername())
                .token(jwt)
                .build();

        return new ResponseEntity<>(jwtResponse,HttpStatus.OK);
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
    public ResponseEntity<?> userRegistration(@RequestBody User user) {
        User existingUser = userService.findUserByUsername(user.getUsername());

        if (existingUser!=null)
            return ResponseEntity.badRequest().body("User already exists!");

        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/admin/register")
    public ResponseEntity<?> adminRegistration(@RequestBody User user) {
        User existingUser = userService.findUserByUsername(user.getUsername());

        if (existingUser!=null)
            return ResponseEntity.badRequest().body("User already exists!");

        userService.saveAdmin(user);
        return new ResponseEntity<>(HttpStatus.OK);
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
