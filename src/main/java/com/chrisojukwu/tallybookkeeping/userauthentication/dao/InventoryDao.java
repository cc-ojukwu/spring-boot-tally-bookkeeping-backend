package com.chrisojukwu.tallybookkeeping.userauthentication.dao;


import com.chrisojukwu.tallybookkeeping.userauthentication.models.NetworkIncome;
import com.chrisojukwu.tallybookkeeping.userauthentication.models.NetworkStockItem;
import com.chrisojukwu.tallybookkeeping.userauthentication.repository.IncomeRepository;
import com.chrisojukwu.tallybookkeeping.userauthentication.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryDao {

    @Autowired
    InventoryRepository inventoryRepo;

    public List<NetworkStockItem> getAllStock(Long userId) {

        return inventoryRepo.findByUserId(userId);
    }

    public void saveStock(NetworkStockItem stockItem) {
        inventoryRepo.save(stockItem);
    }

    @Transactional
    public void updateStock(NetworkStockItem stockItem) {
        //inventoryRepo.delete(stockItem);
        inventoryRepo.save(stockItem);
    }

    public void deleteStock(NetworkStockItem stockItem) {
        inventoryRepo.delete(stockItem);
    }
}
