package com.chrisojukwu.tallybookkeeping.userauthentication.dao;


import com.chrisojukwu.tallybookkeeping.userauthentication.models.NetworkIncome;
import com.chrisojukwu.tallybookkeeping.userauthentication.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IncomeDao {

    @Autowired
    IncomeRepository incomeRepo;

    public List<NetworkIncome> getAllIncome(Long userId) {

        return incomeRepo.findByUserId(userId);
    }

    public void saveIncome(NetworkIncome income) {
        incomeRepo.save(income);
    }

    @Transactional
    public void updateIncome(NetworkIncome income) {
        //incomeRepo.delete(income);
        incomeRepo.save(income);
    }

    public void deleteIncome(NetworkIncome income) {
        incomeRepo.delete(income);
    }
}
