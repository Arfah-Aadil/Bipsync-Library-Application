package library.libraryapp.activeusers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ActiveUsersDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    int ActiveUsersSum(){
        String sql = "SELECT COUNT(*) FROM users WHERE Status = 'ACTIVE'";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
