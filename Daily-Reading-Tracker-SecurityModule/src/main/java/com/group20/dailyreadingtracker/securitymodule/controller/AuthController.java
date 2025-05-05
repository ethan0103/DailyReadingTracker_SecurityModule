package com.group20.dailyreadingtracker.securitymodule.controller;

import com.group20.dailyreadingtracker.securitymodule.model.User;
import com.group20.dailyreadingtracker.securitymodule.service.JwtService;
import com.group20.dailyreadingtracker.securitymodule.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        User newUser = userService.registerUser(username, password);
        return "User registered successfully with username: " + newUser.getUsername();
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password) {
        User user = userService.findByUsername(username);
        if (user != null && userService.verifyPassword(password, user.getPassword())) {
            String jwtToken = jwtService.generateJwtToken(username);
            return "Login successful, JWT: " + jwtToken;
        }
        return "Invalid credentials!";
    }
}
