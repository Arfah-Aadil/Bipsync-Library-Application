package library.libraryapp.repository;

import library.libraryapp.model.userTransaction;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface userTransactionRepository {
    void save(userTransaction transaction) throws SQLException;
    List<userTransaction> findPendingTransactionsByItemId(int itemId);
    void updateTransactionStatus(int transactionId, String status) throws SQLException;
    List<userTransaction> findAll() throws SQLException;
    List<userTransaction> findByUserIdAndCheckinDateIsNull(int userId) throws SQLException;
    Optional<userTransaction> findById(int id) throws SQLException;
}
