package com.chrisojukwu.tallybookkeeping.userauthentication.repository;

import com.chrisojukwu.tallybookkeeping.userauthentication.models.NetworkExpense;
import com.chrisojukwu.tallybookkeeping.userauthentication.models.NetworkIncome;
import com.chrisojukwu.tallybookkeeping.userauthentication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<NetworkExpense, String> {

    //@Query("SELECT * FROM NetworkExpense WHERE user_id = :id")
    List<NetworkExpense> findByUserId(Long id);

}
