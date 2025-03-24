//package library.libraryapp.repository;
//
//import library.libraryapp.model.User;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class UserRepository {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public UserRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<User> findAll() {
//        String sql = "SELECT * FROM Users";
//        return jdbcTemplate.query(sql, userRowMapper());
//    }
//
//    public int deleteById(int userId) {
//        String sql = "DELETE FROM Users WHERE UserID = ?";
//        return jdbcTemplate.update(sql, userId);
//    }
//
//    public void saveUser(User user) {
//        String sql = "INSERT INTO Users (Username, Password, Email, Role, Status) VALUES (?, ?, ?, ?, ?)";
//        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail(), user.getRole(), user.getStatus());
//    }
//
//    private RowMapper<User> userRowMapper() {
//        return (rs, rowNum) -> {
//            User user = new User();
//            user.setUserId(rs.getInt("UserID"));
//            user.setUsername(rs.getString("Username"));
//            user.setPassword(rs.getString("Password"));
//            user.setEmail(rs.getString("Email"));
//            user.setRole(rs.getString("Role"));
//            user.setStatus(rs.getString("Status"));
//            return user;
//        };
//    }
//}
package library.libraryapp.repository;

import library.libraryapp.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findAll() {
        String sql = "SELECT * FROM Users";
        return jdbcTemplate.query(sql, userRowMapper());
    }

    public int deleteById(int userId) {
        String sql = "DELETE FROM Users WHERE UserID = ?";
        return jdbcTemplate.update(sql, userId);
    }

    public void saveUser(User user) {
        String sql = "INSERT INTO Users (Username, Password, Email, Role, Status) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail(), user.getRole(), user.getStatus());
    }
    public User findById(Long userId) {
        String sql = "SELECT * FROM Users WHERE UserID = ?";
        return jdbcTemplate.queryForObject(sql, userRowMapper(), userId);
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setUserId(rs.getInt("UserID"));
            user.setUsername(rs.getString("Username"));
            user.setPassword(rs.getString("Password"));
            user.setEmail(rs.getString("Email"));
            user.setRole(rs.getString("Role"));
            user.setStatus(rs.getString("Status"));
            return user;
        };
    }
}
