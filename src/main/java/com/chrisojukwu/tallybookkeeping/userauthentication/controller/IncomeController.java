package com.chrisojukwu.tallybookkeeping.userauthentication.controller;

import com.chrisojukwu.tallybookkeeping.userauthentication.dao.IncomeDao;
import com.chrisojukwu.tallybookkeeping.userauthentication.models.NetworkIncome;
import com.chrisojukwu.tallybookkeeping.userauthentication.models.User;
import com.chrisojukwu.tallybookkeeping.userauthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "user/income", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class IncomeController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private IncomeDao incomeDao;


    @GetMapping("/get-all-income")
    public ResponseEntity<List<NetworkIncome>> getAllIncome(HttpServletRequest request) {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepo.findByEmail(email);

            List<NetworkIncome> incomeList = incomeDao.getAllIncome(user.getId());

            return new ResponseEntity<>(incomeList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create-income")
    public ResponseEntity<Map<String, String>> createIncome(@RequestBody NetworkIncome income) {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepo.findByEmail(email);
            income.setUser(user);
            incomeDao.saveIncome(income);
            return new ResponseEntity<>(Collections.singletonMap("response","income saved"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("response","error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete-income")
    public ResponseEntity<Map<String, String>> deleteIncome(@RequestBody NetworkIncome income) {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepo.findByEmail(email);
            income.setUser(user);
            incomeDao.deleteIncome(income);

            return new ResponseEntity<>(Collections.singletonMap("response","income deleted"), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("response","error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update-income")
    public ResponseEntity<Map<String, String>> updateIncome(@RequestBody NetworkIncome income) {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepo.findByEmail(email);
            income.setUser(user);
            incomeDao.updateIncome(income);
            return new ResponseEntity<>(Collections.singletonMap("response","income updated"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("response","error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
