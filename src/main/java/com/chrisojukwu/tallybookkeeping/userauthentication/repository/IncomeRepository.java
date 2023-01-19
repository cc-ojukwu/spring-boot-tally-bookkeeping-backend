package com.chrisojukwu.tallybookkeeping.userauthentication.repository;

import com.chrisojukwu.tallybookkeeping.userauthentication.models.NetworkIncome;
import com.chrisojukwu.tallybookkeeping.userauthentication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<NetworkIncome, String> {

    //@Query("SELECT * FROM NetworkIncome WHERE user_id = :id")
    List<NetworkIncome> findByUserId(Long id);

}
