package library.libraryapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserHomeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserHomeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String findUserNameById(int userId) {
        String sql = "SELECT UserName FROM Users WHERE UserID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{userId}, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public List<Map<String, Object>> findRecentActivityByUserId(int userId) {
        String sql = "SELECT t.TransactionID, t.CheckoutDate, t.CheckinDate, i.Type, i.Model, i.Location " +
                "FROM transactions t " +
                "JOIN Inventory i ON t.ItemID = i.ItemID " +
                "WHERE t.UserID = ? " +
                "ORDER BY t.CheckoutDate DESC, t.CheckinDate DESC " +
                "LIMIT 6";
        return jdbcTemplate.queryForList(sql, userId);
    }

    public List<Map<String, Object>> findUpcomingCheckinsByUserId(int userId) {
        String sql = "SELECT t.TransactionID, t.DueDate, i.Type, i.Model, i.Location " +
                "FROM transactions t " +
                "JOIN Inventory i ON t.ItemID = i.ItemID " +
                "WHERE t.UserID = ? AND t.CheckinDate IS NULL AND t.DueDate >= CURRENT_DATE " +
                "ORDER BY t.DueDate ASC";
        return jdbcTemplate.queryForList(sql, userId);
    }
}