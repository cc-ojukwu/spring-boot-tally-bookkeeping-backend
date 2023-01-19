package com.chrisojukwu.tallybookkeeping.userauthentication.dao;


import com.chrisojukwu.tallybookkeeping.userauthentication.models.NetworkExpense;
import com.chrisojukwu.tallybookkeeping.userauthentication.models.NetworkIncome;
import com.chrisojukwu.tallybookkeeping.userauthentication.repository.ExpenseRepository;
import com.chrisojukwu.tallybookkeeping.userauthentication.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExpenseDao {

    @Autowired
    ExpenseRepository expenseRepo;

    public List<NetworkExpense> getAllExpense(Long userId) {

        return expenseRepo.findByUserId(userId);
    }

    public void saveExpense(NetworkExpense expense) {

        expenseRepo.save(expense);
    }

    @Transactional
    public void updateExpense(NetworkExpense expense) {
        //expenseRepo.delete(expense);
        expenseRepo.save(expense);
    }

    public void deleteExpense(NetworkExpense expense) {
        expenseRepo.delete(expense);
    }
}
