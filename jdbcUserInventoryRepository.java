package library.libraryapp.repository;

import library.libraryapp.model.userInventoryItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class jdbcUserInventoryRepository implements userInventoryRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<userInventoryItem> rowMapper = (rs, rowNum) -> {
        userInventoryItem item = new userInventoryItem();
        item.setItemId(rs.getInt("ItemID"));
        item.setType(rs.getString("Type"));
        item.setModel(rs.getString("Model"));
        item.setStatus(rs.getString("Status"));
        item.setLocation(rs.getString("Location"));
        item.setCompany(rs.getString("Company"));
        item.setAcquisitionDate(rs.getTimestamp("AcquisitionDate").toLocalDateTime());
        item.setStock(rs.getInt("Stock"));
        return item;
    };

    public jdbcUserInventoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<userInventoryItem> findAll() {
        String sql = "SELECT * FROM Inventory";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<userInventoryItem> searchByQuery(String query) {
        String sql = "SELECT * FROM Inventory WHERE LOWER(Type) LIKE LOWER(?) OR LOWER(Model) LIKE LOWER(?) OR LOWER(Status) LIKE LOWER(?) OR LOWER(Location) LIKE LOWER(?) OR LOWER(Company) LIKE LOWER(?)";
        Object[] params = new Object[]{"%" + query.toLowerCase() + "%", "%" + query.toLowerCase() + "%", "%" + query.toLowerCase() + "%", "%" + query.toLowerCase() + "%", "%" + query.toLowerCase() + "%"};
        return jdbcTemplate.query(sql, params, rowMapper);
    }

    @Override
    public List<userInventoryItem> searchByQuery(String query, String type, String status) {
        StringBuilder sql = new StringBuilder("SELECT * FROM Inventory WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (query != null && !query.isEmpty()) {
            sql.append(" AND (LOWER(Type) LIKE LOWER(?) OR LOWER(Model) LIKE LOWER(?) OR LOWER(Status) LIKE LOWER(?) OR LOWER(Location) LIKE LOWER(?) OR LOWER(Company) LIKE LOWER(?))");
            for (int i = 0; i < 5; i++) {
                params.add("%" + query.toLowerCase() + "%");
            }
        }

        if (type != null && !type.isEmpty()) {
            sql.append(" AND LOWER(Type) = LOWER(?)");
            params.add(type.toLowerCase());
        }

        if (status != null && !status.isEmpty()) {
            sql.append(" AND LOWER(Status) = LOWER(?)");
            params.add(status.toLowerCase());
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), rowMapper);
    }

    @Override
    public Optional<userInventoryItem> findById(int id) {
        String sql = "SELECT * FROM Inventory WHERE ItemID = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, rowMapper).stream().findFirst();
    }

@Override
    public void save(userInventoryItem item) {
        String sql = "UPDATE Inventory SET Type = ?, Model = ?, Status = ?, Location = ?, Company = ?, AcquisitionDate = ?, Stock = ? WHERE ItemID = ?";
        jdbcTemplate.update(sql, item.getType(), item.getModel(), item.getStatus(), item.getLocation(), item.getCompany(), java.sql.Timestamp.valueOf(item.getAcquisitionDate()), item.getStock(), item.getItemId());
    }
}
