package com.chrisojukwu.tallybookkeeping.userauthentication;

import com.chrisojukwu.tallybookkeeping.userauthentication.models.Provider;
import com.chrisojukwu.tallybookkeeping.userauthentication.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepo;
//
//    public void processOAuthPostLogin(String email) {
//        User existingUser = userRepo.findByEmail(email);
//
//        if (existingUser == null) {
//            User newUser = new User();
//            newUser.setEmail(email);
//            newUser.setProvider(Provider.GOOGLE);
//            newUser.setEnabled(true);
//            newUser.setPassword("");
//            newUser.setRole("ROLE_USER");
//
//            userRepo.save(newUser);
//        }
//    }
//}
