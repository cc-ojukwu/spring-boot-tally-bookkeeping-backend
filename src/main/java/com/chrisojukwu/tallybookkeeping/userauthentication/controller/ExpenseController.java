package com.chrisojukwu.tallybookkeeping.userauthentication.controller;

import com.chrisojukwu.tallybookkeeping.userauthentication.dao.ExpenseDao;
import com.chrisojukwu.tallybookkeeping.userauthentication.models.NetworkExpense;
import com.chrisojukwu.tallybookkeeping.userauthentication.models.User;
import com.chrisojukwu.tallybookkeeping.userauthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user/expense")
public class ExpenseController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    ExpenseDao expenseDao;

    @GetMapping("/get-all-expense")
    public ResponseEntity<List<NetworkExpense>> getAllExpense(HttpServletRequest request) {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepo.findByEmail(email);

            List<NetworkExpense> expenseList = expenseDao.getAllExpense(user.getId());

            return new ResponseEntity<>(expenseList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create-expense")
    public ResponseEntity<Map<String, String>> createExpense(@RequestBody NetworkExpense expense) {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepo.findByEmail(email);
            expense.setUser(user);
            expenseDao.saveExpense(expense);

            return new ResponseEntity<>(Collections.singletonMap("response","expense saved"), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("response","error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete-expense")
    public ResponseEntity<Map<String, String>> deleteExpense(@RequestBody NetworkExpense expense) {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepo.findByEmail(email);
            expense.setUser(user);
            expenseDao.deleteExpense(expense);

            return new ResponseEntity<>(Collections.singletonMap("response","expense deleted"), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("response","error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update-expense")
    public ResponseEntity<Map<String, String>> updateExpense(@RequestBody NetworkExpense expense) {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepo.findByEmail(email);
            expense.setUser(user);
            expenseDao.updateExpense(expense);
            return new ResponseEntity<>(Collections.singletonMap("response","expense updated"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("response","error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
