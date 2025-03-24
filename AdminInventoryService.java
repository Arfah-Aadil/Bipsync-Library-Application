package library.libraryapp.service;

import library.libraryapp.model.AdminInventoryItem;
import library.libraryapp.model.userTransaction;
import library.libraryapp.repository.AdminInventoryRepository;
import library.libraryapp.repository.userTransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdminInventoryService {

    private final AdminInventoryRepository adminInventoryRepository;
    private final userTransactionRepository transactionRepository;

    public AdminInventoryService(AdminInventoryRepository adminInventoryRepository, userTransactionRepository transactionRepository) {
        this.adminInventoryRepository = adminInventoryRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<AdminInventoryItem> findAllInventoryItems() {
        return adminInventoryRepository.findAll();
    }

    public List<AdminInventoryItem> searchInventoryItems(String query, String type, String status, String sortField, String sortOrder) {
        if (query != null && type != null && status != null) {
            return adminInventoryRepository.searchByQuery(query, type, status);
        } else if (query != null) {
            return adminInventoryRepository.searchByQuery(query);
        } else {
            return adminInventoryRepository.findAll();
        }
    }

    public void updateItem(int itemId, int stock, String location, String status) throws Exception {
        AdminInventoryItem item = adminInventoryRepository.findById(itemId).orElseThrow(() -> new Exception("Item not found"));
        item.setStock(stock);
        item.setLocation(location);
        item.setStatus(status);
        adminInventoryRepository.save(item);
    }


    public void approveItem(int itemId) throws Exception {
        List<userTransaction> pendingTransactions = transactionRepository.findPendingTransactionsByItemId(itemId);

        if (pendingTransactions.isEmpty()) {
            throw new Exception("No pending transactions for this item.");
        }

        AdminInventoryItem item = adminInventoryRepository.findById(itemId).orElseThrow(() -> new Exception("Item not found"));
        item.setStatus("Approved");
        adminInventoryRepository.save(item);

        for (userTransaction transaction : pendingTransactions) {
            transactionRepository.updateTransactionStatus(transaction.getTransactionId(), "Approved");
        }
    }



    public void deleteItem(int itemId) throws Exception {
        AdminInventoryItem item = adminInventoryRepository.findById(itemId).orElseThrow(() -> new Exception("Item not found"));
        adminInventoryRepository.delete(item.getItemId());
    }

    public boolean hasPendingTransactions(int itemId) {
        List<userTransaction> pendingTransactions = transactionRepository.findPendingTransactionsByItemId(itemId);
        return !pendingTransactions.isEmpty();
    }

    public void addItem(String type, String model, String status, int stock, String description, String location, String company, String acquisitionDate) throws Exception {
        try {
            AdminInventoryItem newItem = new AdminInventoryItem();
            newItem.setType(type);
            newItem.setModel(model);
            newItem.setStatus(status);
            newItem.setStock(stock);
            newItem.setDescription(description);
            newItem.setLocation(location);
            newItem.setCompany(company);
            newItem.setAcquisitionDate(LocalDate.parse(acquisitionDate).atStartOfDay());

            adminInventoryRepository.saveNewItem(newItem);
        } catch (Exception e) {
            
            System.err.println("Error adding item: " + e.getMessage());
            throw new Exception("Failed to add item. Please check the input data and try again.");
        }
    }

}