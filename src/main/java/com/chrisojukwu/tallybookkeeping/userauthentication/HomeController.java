package com.chrisojukwu.tallybookkeeping.userauthentication;

import com.chrisojukwu.tallybookkeeping.userauthentication.models.JWTToken;
import com.chrisojukwu.tallybookkeeping.userauthentication.models.LoginCredentials;
import com.chrisojukwu.tallybookkeeping.userauthentication.models.Provider;
import com.chrisojukwu.tallybookkeeping.userauthentication.models.User;
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
import java.util.logging.LogManager;

@RestController
public class HomeController {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home() {
        return "Hello World";
    }

    @GetMapping("/user")
    public String user() {
        return "Hello User";
    }

    @GetMapping("/ghost")
    public String ghost() {
        return "Hello Ghost";
    }

    @PostMapping("/test")
    public String postTest() {
        return "Hello Test";
    }


    @PostMapping("/process-register")
    public ResponseEntity<String> processRegister(@RequestBody User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        if (userRepo.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>("Email already registered", HttpStatus.BAD_REQUEST);
        }

        try {
            userRepo.save(user);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value="/auth/login")
    public ResponseEntity<Map<String, Object>> loginHandler(@RequestBody LoginCredentials body) throws InterruptedException {
        Thread.sleep(4000);

        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

            authManager.authenticate(authToken);

            String token = jwtUtil.generateToken(body.getEmail());

            return new ResponseEntity<>(Collections.singletonMap("jwt-token", token), HttpStatus.OK);

        } catch (AuthenticationException authExc) {
            return new ResponseEntity<>(Collections.singletonMap("jwt-token", ""), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/auth/oauth2/logintest")
    public ResponseEntity<Map<String, Object>> googleLogin(HttpServletRequest request) throws GeneralSecurityException, IOException {

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singleton("1053376402234-4jnl17jv924e26s2b8c38an7oqjvqevd.apps.googleusercontent.com"))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        String headerToken = request.getHeader("google_id_token");
        if (headerToken != null) {
            System.out.println(headerToken);
            GoogleIdToken idToken = verifier.verify(headerToken);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                if (userRepo.findByEmail(payload.getEmail()) != null) {
                    String jwtToken = jwtUtil.generateToken(payload.getEmail());

                    return new ResponseEntity<>(Collections.singletonMap("jwt-token", jwtToken), HttpStatus.OK);
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

                userRepo.save(newUser);

                String jwtToken = jwtUtil.generateToken(payload.getEmail());

                return new ResponseEntity<>(Collections.singletonMap("jwt-token", jwtToken), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Collections.singletonMap("message", "invalid token"), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(Collections.singletonMap("message", "no token found"), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/info")
    public User getUserDetails() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepo.findByEmail(email);
    }
}
