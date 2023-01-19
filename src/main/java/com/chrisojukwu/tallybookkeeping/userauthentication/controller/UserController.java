package com.chrisojukwu.tallybookkeeping.userauthentication.controller;

import com.chrisojukwu.tallybookkeeping.userauthentication.models.*;
import com.chrisojukwu.tallybookkeeping.userauthentication.repository.UserRepository;
import com.chrisojukwu.tallybookkeeping.userauthentication.security.JWTUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthenticationManager authManager;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;


    @PostMapping("/email-create-account")
    public ResponseEntity<Map<String, String>> emailCreateAccount(@RequestBody User user) throws InterruptedException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        if (userRepo.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>(Collections.singletonMap("response","email already registered"), HttpStatus.BAD_REQUEST);
        }

        try {
            userRepo.save(user);
            return new ResponseEntity<>(Collections.singletonMap("response","success"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("response","error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/email-sign-in")
    public ResponseEntity<Map<String, Object>> loginHandler(@RequestBody LoginCredentials body) throws InterruptedException {

        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

            authManager.authenticate(authToken);

            String token = jwtUtil.generateToken(body.getEmail());

            return new ResponseEntity<>(Collections.singletonMap("jwt_token", token), HttpStatus.OK);

        } catch (AuthenticationException authExc) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/forgot-password")
    public ResponseEntity<Map<String, Object>> forgotPassword(@RequestBody ForgotPassword forgotPassword) throws InterruptedException {

        try {
            User user = userRepo.findByEmail(forgotPassword.getEmail());

            if ( user == null) {
                return new ResponseEntity<>(Collections.singletonMap("response","this email is not registered"), HttpStatus.BAD_REQUEST);
            }

            final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            SecureRandom rnd = new SecureRandom();
            StringBuilder newPassword = new StringBuilder(6);
            for(int i = 0; i < 6; i++)
                newPassword.append(AB.charAt(rnd.nextInt(AB.length())));

            Logger.getLogger("password").log(Level.INFO, newPassword.toString());

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encryptedPassword = passwordEncoder.encode(newPassword);

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(from);
            message.setTo(forgotPassword.getEmail());
            message.setSubject("Forgot Password");
            message.setText("Hello. We have reset your password to: " +newPassword );

            mailSender.send(message);

            user.setPassword(encryptedPassword);
            userRepo.save(user);

            return new ResponseEntity<>(Collections.singletonMap("response","Password has been reset. Check email"), HttpStatus.OK);

        } catch (AuthenticationException authExc) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/update-user-info")
    public ResponseEntity<User> updateUserInfo(@RequestBody User user) throws InterruptedException {

        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User dbUser = userRepo.findByEmail(email);

            dbUser.setFirstName(user.getFirstName());
            dbUser.setLastName(user.getLastName());
            dbUser.setBusinessName(user.getBusinessName());
            dbUser.setBusinessAddress(user.getBusinessAddress());
            dbUser.setBusinessPhone(user.getBusinessPhone());

            User savedUser = userRepo.save(dbUser);

            return new ResponseEntity<>(savedUser, HttpStatus.OK);

        } catch (AuthenticationException authExc) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/get-user-info")
    public ResponseEntity<User> getUserInfo() throws InterruptedException {

        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            User user = userRepo.findByEmail(email);

            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (AuthenticationException authExc) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/change-email")
    public ResponseEntity<Map<String, Object>> changeEmail(@RequestBody User user) throws InterruptedException {

        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User dbUser = userRepo.findByEmail(email);

            dbUser.setEmail(user.getEmail());

            String token = jwtUtil.generateToken(user.getEmail());

            userRepo.save(dbUser);

            return new ResponseEntity<>(Map.of("jwt_token", token, "email", user.getEmail()), HttpStatus.OK);

        } catch (AuthenticationException authExc) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/change-password")
    public ResponseEntity<Map<String, String>> changeUserPassword(@RequestBody ChangePassword changePassword) {

        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepo.findByEmail(email);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(email, changePassword.getOldPassword());

            authManager.authenticate(authToken);

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(changePassword.getNewPassword());

            user.setPassword(encodedPassword);

            userRepo.save(user);

            return new ResponseEntity<>(Collections.singletonMap("response","password changed"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("response","error"), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/oauth2/google-sign-in")
    public ResponseEntity<Map<String, Object>> googleLogin(HttpServletRequest request) throws GeneralSecurityException, IOException {

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singleton("1053376402234-4jnl17jv924e26s2b8c38an7oqjvqevd.apps.googleusercontent.com"))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        String headerToken = request.getHeader("google_id_token");
        if (headerToken != null) {
            GoogleIdToken idToken = verifier.verify(headerToken);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                if (userRepo.findByEmail(payload.getEmail()) != null) {
                    String jwtToken = jwtUtil.generateToken(payload.getEmail());

                    //return new ResponseEntity<>(Collections.singletonMap("jwt-token", jwtToken), HttpStatus.OK);
                    return new ResponseEntity<>(Map.of("jwt_token", jwtToken, "email", payload.getEmail()), HttpStatus.OK);
                }
                User newUser = new User();
                newUser.setRole("ROLE_USER");
                newUser.setEmail(payload.getEmail());
                newUser.setProvider(Provider.GOOGLE);
                newUser.setUserId(payload.getSubject());
                newUser.setEnabled(true);
                newUser.setPassword("google");
                newUser.setFirstName(((String) payload.get("given_name")));
                newUser.setLastName(((String) payload.get("family_name")));
                newUser.setBusinessName("My Business Name");
                newUser.setBusinessAddress("");
                newUser.setBusinessPhone("");

                userRepo.save(newUser);

                String jwtToken = jwtUtil.generateToken(payload.getEmail());

                //return new ResponseEntity<>(Collections.singletonMap("jwt-token", jwtToken), HttpStatus.OK);
                return new ResponseEntity<>(Map.of("jwt_token", jwtToken, "email", payload.getEmail()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Collections.singletonMap("message", "invalid token"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "no token found"), HttpStatus.BAD_REQUEST);
        }
    }
}