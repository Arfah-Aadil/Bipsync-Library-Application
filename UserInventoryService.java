package library.libraryapp.service;

import library.libraryapp.model.userInventoryItem;
import library.libraryapp.model.userTransaction;
import library.libraryapp.repository.userInventoryRepository;
import library.libraryapp.repository.userTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class UserInventoryService {

    private final userInventoryRepository repository;
    private final userTransactionRepository transactionRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserInventoryService.class);
    @Autowired
    public UserInventoryService(userInventoryRepository repository, userTransactionRepository transactionRepository) {
        this.repository = repository;
        this.transactionRepository = transactionRepository;
    }


    public List<userInventoryItem> findAllInventoryItems() {
        return repository.findAll();
    }


    public List<userInventoryItem> searchInventoryItems(String query, String type, String status, String sortField, String sortOrder) {
        List<userInventoryItem> items = repository.searchByQuery(query, type, status);

        if (sortField != null && !sortField.isEmpty()) {
            items.sort((item1, item2) -> {
                int comparison = 0;
                switch (sortField) {
                    case "type":
                        comparison = item1.getType().compareTo(item2.getType());
                        break;
                    case "status":
                        comparison = item1.getStatus().compareTo(item2.getStatus());
                        break;
                    case "company":
                        comparison = item1.getCompany().compareTo(item2.getCompany());
                        break;
                    case "acquisitionDate":
                        comparison = item1.getAcquisitionDate().compareTo(item2.getAcquisitionDate());
                        break;
                }
                return "desc".equals(sortOrder) ? -comparison : comparison;
            });
        }
        return items;
    }

    public List<userTransaction> findAllTransactions() throws SQLException {
        return transactionRepository.findAll();
    }

    @Transactional
    public void checkoutItem(int itemId, int userId) throws Exception {
        userInventoryItem item = repository.findById(itemId).orElseThrow(() -> new Exception("Item not found"));
        if (item.getStock() <= 0) {
            throw new Exception("Item out of stock");
        }
        item.setStock(item.getStock() - 1);
        repository.save(item);
        userTransaction transaction = new userTransaction(itemId, userId, "Pending");
        transactionRepository.save(transaction);
    }

    @Transactional
    public void checkinItem(int transactionId) throws Exception {
        Logger logger = LoggerFactory.getLogger(UserInventoryService.class);

        userTransaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new Exception("Transaction not found"));

        logger.info("Checking in transaction: {}", transaction);

        transaction.setCheckinDate(LocalDate.now());
        transaction.setStatus("Checked In"); // Update status to indicate check-in
        transactionRepository.save(transaction);

        userInventoryItem item = repository.findById(transaction.getItemId())
                .orElseThrow(() -> new Exception("Item not found"));
        item.setStock(item.getStock() + 1);
        repository.save(item);

        logger.info("Item after check-in: {}", item);
    }
    public List<userTransaction> findCheckedOutItemsByUser(int userId) throws SQLException {
        return transactionRepository.findByUserIdAndCheckinDateIsNull(userId);
    }
}