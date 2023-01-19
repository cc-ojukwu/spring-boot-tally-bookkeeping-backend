package com.chrisojukwu.tallybookkeeping.userauthentication.controller;

import com.chrisojukwu.tallybookkeeping.userauthentication.models.LoginCredentials;
import com.chrisojukwu.tallybookkeeping.userauthentication.models.Provider;
import com.chrisojukwu.tallybookkeeping.userauthentication.models.User;
import com.chrisojukwu.tallybookkeeping.userauthentication.repository.UserRepository;
import com.chrisojukwu.tallybookkeeping.userauthentication.security.JWTUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
public class TestController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AuthenticationManager authManager;


    @GetMapping("/")
    public String home() {
        return "Hello World";
    }


    @GetMapping("/info")
    public User getUserDetails() {
        Logger.getLogger("myLogger").log(Level.INFO, "in info method");
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepo.findByEmail(email);

    }
}