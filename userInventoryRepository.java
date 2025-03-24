package library.libraryapp.repository;

import library.libraryapp.model.userInventoryItem;
import java.util.List;



import library.libraryapp.model.userInventoryItem;
import java.util.List;
import java.util.Optional;

public interface userInventoryRepository {
    List<userInventoryItem> findAll();
    List<userInventoryItem> searchByQuery(String query);
    List<userInventoryItem> searchByQuery(String query, String type, String status);
    Optional<userInventoryItem> findById(int id);
    void save(userInventoryItem item);
}