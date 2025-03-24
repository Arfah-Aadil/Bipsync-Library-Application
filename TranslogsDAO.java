package library.libraryapp.Translogs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TranslogsDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final RowMapper<Translogs> translogsRowMapper = new RowMapper<Translogs>() {
        @Override
        public Translogs mapRow(ResultSet rs, int rowNum) throws SQLException {
            Translogs translogs = new Translogs();
            translogs.setTransactionID(rs.getInt("TransactionID"));
            translogs.setItemID(rs.getInt("ItemID"));
            translogs.setUserID(rs.getInt("UserID"));
            translogs.setCheckoutdate(rs.getDate("CheckoutDate"));
            translogs.setDuedate(rs.getDate("DueDate"));
            translogs.setCheckindate(rs.getDate("CheckinDate"));
            translogs.setStatus(rs.getString("Status"));
            return translogs;
        }
    };
    public List<Translogs> getAllTransactions(){
        String sql = "SELECT * FROM Transactions";
        return jdbcTemplate.query(sql,translogsRowMapper);
    }
}

