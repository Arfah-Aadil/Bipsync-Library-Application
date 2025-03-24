package library.libraryapp.repository;

import library.libraryapp.model.AdminInventoryItem;
import java.util.List;
import java.util.Optional;

public interface AdminInventoryRepository {
    List<AdminInventoryItem> findAll();
    List<AdminInventoryItem> searchByQuery(String query);
    List<AdminInventoryItem> searchByQuery(String query, String type, String status);
    Optional<AdminInventoryItem> findById(int id);
    void save(AdminInventoryItem item);
    void delete(int itemId);
    void saveNewItem(AdminInventoryItem item);
}