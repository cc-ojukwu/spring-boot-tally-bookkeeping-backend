package com.chrisojukwu.tallybookkeeping.userauthentication.controller;

import com.chrisojukwu.tallybookkeeping.userauthentication.dao.IncomeDao;
import com.chrisojukwu.tallybookkeeping.userauthentication.dao.InventoryDao;
import com.chrisojukwu.tallybookkeeping.userauthentication.models.NetworkIncome;
import com.chrisojukwu.tallybookkeeping.userauthentication.models.NetworkStockItem;
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

@RequestMapping("user/inventory")
@RestController
public class InventoryController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private InventoryDao inventoryDao;


    @GetMapping("/get-all-stock")
    public ResponseEntity<List<NetworkStockItem>> getAllStock(HttpServletRequest request) {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepo.findByEmail(email);

            List<NetworkStockItem> stockList = inventoryDao.getAllStock(user.getId());

            return new ResponseEntity<>(stockList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create-stock")
    public ResponseEntity<Map<String, String>> createStock(@RequestBody NetworkStockItem stockItem) {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepo.findByEmail(email);
            stockItem.setUser(user);
            inventoryDao.saveStock(stockItem);
            return new ResponseEntity<>(Collections.singletonMap("response","stock saved"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("response","error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete-stock")
    public ResponseEntity<Map<String, String>> deleteStock(@RequestBody NetworkStockItem stockItem) {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepo.findByEmail(email);
            stockItem.setUser(user);
            inventoryDao.deleteStock(stockItem);

            return new ResponseEntity<>(Collections.singletonMap("response","stock deleted"), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("response","error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update-stock")
    public ResponseEntity<Map<String, String>> updateStock(@RequestBody NetworkStockItem stockItem) {
        try {
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepo.findByEmail(email);
            stockItem.setUser(user);
            inventoryDao.updateStock(stockItem);
            return new ResponseEntity<>(Collections.singletonMap("response","stock updated"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("response","error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //DELETE FROM `user-details-database`.income_table WHERE record_id = "record-495678";

}
