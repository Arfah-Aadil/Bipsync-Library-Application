package library.libraryapp.repository;

import library.libraryapp.model.userTransaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class jdbcUserTransactionRepository implements userTransactionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public jdbcUserTransactionRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    @Override
    public void save(userTransaction transaction) throws SQLException {
        if (transaction.getTransactionId() == 0) {
            // Insert new transaction
            String sql = "INSERT INTO transactions (ItemID, UserID, CheckoutDate, DueDate, CheckinDate, Status) VALUES (?, ?, ?, ?, ?, ?)";
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, transaction.getItemId());
                preparedStatement.setInt(2, transaction.getUserId());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(transaction.getCheckoutDate()));
                preparedStatement.setDate(4, Date.valueOf(transaction.getDueDate()));
                if (transaction.getCheckinDate() != null) {
                    preparedStatement.setDate(5, Date.valueOf(transaction.getCheckinDate()));
                } else {
                    preparedStatement.setNull(5, Types.DATE);
                }
                preparedStatement.setString(6, transaction.getStatus());
                preparedStatement.executeUpdate();

                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        transaction.setTransactionId(generatedKeys.getInt(1));
                    }
                }
            }
        } else {
            // Update existing transaction
            String sql = "UPDATE transactions SET CheckinDate = ?, Status = ? WHERE TransactionID = ?";
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                if (transaction.getCheckinDate() != null) {
                    preparedStatement.setDate(1, Date.valueOf(transaction.getCheckinDate()));
                } else {
                    preparedStatement.setNull(1, Types.DATE);
                }
                preparedStatement.setString(2, transaction.getStatus());
                preparedStatement.setInt(3, transaction.getTransactionId());
                preparedStatement.executeUpdate();
            }
        }
    }

    @Override
    public List<userTransaction> findPendingTransactionsByItemId(int itemId) {
        String sql = "SELECT * FROM transactions WHERE itemid = ? AND status = 'Pending'";
        return jdbcTemplate.query(sql, new Object[]{itemId}, transactionRowMapper);
    }

    @Override
    public void updateTransactionStatus(int transactionId, String status) {
        String sql = "UPDATE transactions SET status = ? WHERE transactionid = ?";
        jdbcTemplate.update(sql, status, transactionId);
    }

    @Override
    public List<userTransaction> findAll() throws SQLException {
        String sql = "SELECT * FROM transactions";
        List<userTransaction> transactions = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                userTransaction transaction = mapResultSetToTransaction(resultSet);
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    @Override
    public List<userTransaction> findByUserIdAndCheckinDateIsNull(int userId) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE UserID = ? AND CheckinDate IS NULL";
        List<userTransaction> transactions = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    userTransaction transaction = mapResultSetToTransaction(resultSet);
                    transactions.add(transaction);
                }
            }
        }
        return transactions;
    }

    @Override
    public Optional<userTransaction> findById(int transactionId) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE TransactionID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, transactionId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    userTransaction transaction = mapResultSetToTransaction(resultSet);
                    return Optional.of(transaction);
                }
            }
        }
        return Optional.empty();
    }

    private final RowMapper<userTransaction> transactionRowMapper = (rs, rowNum) -> {
        return mapResultSetToTransaction(rs);
    };

    private userTransaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        userTransaction transaction = new userTransaction();
        transaction.setTransactionId(rs.getInt("TransactionID"));
        transaction.setItemId(rs.getInt("ItemID"));
        transaction.setUserId(rs.getInt("UserID"));
        transaction.setCheckoutDate(rs.getTimestamp("CheckoutDate").toLocalDateTime());
        transaction.setDueDate(rs.getDate("DueDate") != null ? rs.getDate("DueDate").toLocalDate() : null);
        transaction.setCheckinDate(rs.getDate("CheckinDate") != null ? rs.getDate("CheckinDate").toLocalDate() : null);
        transaction.setStatus(rs.getString("Status"));
        return transaction;
    }
}
