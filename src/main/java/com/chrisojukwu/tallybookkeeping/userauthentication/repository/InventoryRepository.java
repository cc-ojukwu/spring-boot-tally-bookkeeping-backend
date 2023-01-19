package com.chrisojukwu.tallybookkeeping.userauthentication.repository;

import com.chrisojukwu.tallybookkeeping.userauthentication.models.NetworkIncome;
import com.chrisojukwu.tallybookkeeping.userauthentication.models.NetworkStockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<NetworkStockItem, String> {

    List<NetworkStockItem> findByUserId(Long id);

}
