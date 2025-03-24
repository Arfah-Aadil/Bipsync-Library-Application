//package library.libraryapp.repository;
//
//import library.libraryapp.model.Transaction;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//import java.util.Date;
//import java.util.List;
//
//@Repository
//public class TransactionRepository {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public TransactionRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<Transaction> findAll() {
//        String sql = "SELECT t.TransactionID, t.UserID, u.Username, i.Model, t.CheckoutDate, t.DueDate, t.CheckinDate, t.Status " +
//                "FROM transactions t " +
//                "JOIN Users u ON t.UserID = u.UserID " +
//                "JOIN Inventory i ON t.ItemID = i.ItemID";
//
//        return jdbcTemplate.query(sql, transactionRowMapper());
//    }
//
//    public List<Transaction> findTransactionsDueBefore(Date date) {
//        String sql = "SELECT t.TransactionID, t.UserID, u.Username, i.Model, t.CheckoutDate, t.DueDate, t.CheckinDate, t.Status " +
//                "FROM transactions t " +
//                "JOIN Users u ON t.UserID = u.UserID " +
//                "JOIN Inventory i ON t.ItemID = i.ItemID " +
//                "WHERE t.DueDate < ? AND t.Status = 'Checked Out'";
//        return jdbcTemplate.query(sql, transactionRowMapper(), new java.sql.Date(date.getTime()));
//    }
//
//    private RowMapper<Transaction> transactionRowMapper() {
//        return (rs, rowNum) -> {
//            Transaction transaction = new Transaction();
//            transaction.setTransactionId(rs.getInt("TransactionID"));
//            transaction.setUserId(rs.getInt("UserID"));
//            transaction.setItemId(rs.getInt("ItemID"));
//            transaction.setCheckoutDate(rs.getTimestamp("CheckoutDate"));
//            transaction.setDueDate(rs.getDate("DueDate"));
//            transaction.setCheckinDate(rs.getDate("CheckinDate"));
//            transaction.setStatus(rs.getString("Status"));
//            return transaction;
//        };
//    }
//}
package library.libraryapp.repository;

import library.libraryapp.model.Transaction;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class TransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Transaction> findAll() {
        String sql = "SELECT t.TransactionID, t.UserID, t.ItemID, u.Username, i.Model, t.CheckoutDate, t.DueDate, t.CheckinDate, t.Status " +
                "FROM Transactions t " +
                "JOIN Users u ON t.UserID = u.UserID " +
                "JOIN Inventory i ON t.ItemID = i.ItemID";

        return jdbcTemplate.query(sql, transactionRowMapper());
    }


    public Transaction findById(Long transactionId) {
        String sql = "SELECT t.TransactionID, t.UserID, u.Username, i.Model, t.CheckoutDate, t.DueDate, t.CheckinDate, t.Status " +
                "FROM Transactions t " +
                "JOIN Users u ON t.UserID = u.UserID " +
                "JOIN Inventory i ON t.ItemID = i.ItemID " +
                "WHERE t.TransactionID = ?";

        try {
            return jdbcTemplate.queryForObject(sql, transactionRowMapper(), transactionId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    public List<Transaction> findTransactionsDueBefore(Date date) {
        String sql = "SELECT t.TransactionID, t.UserID, t.ItemID, u.Username, i.Model, t.CheckoutDate, t.DueDate, t.CheckinDate, t.Status " +
                "FROM transactions t " +
                "JOIN Users u ON t.UserID = u.UserID " +
                "JOIN Inventory i ON t.ItemID = i.ItemID " +
                "WHERE t.DueDate < ? AND t.Status = 'Checked Out'";
        return jdbcTemplate.query(sql, transactionRowMapper(), new java.sql.Date(date.getTime()));
    }

    public RowMapper<Transaction> transactionRowMapper() {
        return (rs, rowNum) -> {
            Transaction transaction = new Transaction();
            transaction.setTransactionID(rs.getInt("TransactionID"));
            transaction.setUserID(rs.getInt("UserID"));
            transaction.setItemID(rs.getInt("ItemID"));
            transaction.setUsername(rs.getString("Username"));
            transaction.setModel(rs.getString("Model"));
            transaction.setCheckoutDate(rs.getTimestamp("CheckoutDate"));
            transaction.setDueDate(rs.getDate("DueDate"));
            transaction.setCheckinDate(rs.getDate("CheckinDate"));
            transaction.setStatus(rs.getString("Status"));

            try {
                transaction.setItemID(rs.getInt("ItemID"));
            } catch (SQLException e) {

            }
            return transaction;
        };
    }

}
