package library.libraryapp.repository;

import library.libraryapp.model.AdminInventoryItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class jdbcAdminInventoryRepository implements AdminInventoryRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<AdminInventoryItem> rowMapper = (rs, rowNum) -> {
        AdminInventoryItem item = new AdminInventoryItem();
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

    public jdbcAdminInventoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AdminInventoryItem> findAll() {
        String sql = "SELECT * FROM Inventory";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<AdminInventoryItem> searchByQuery(String query) {
        String sql = "SELECT * FROM Inventory WHERE LOWER(Type) LIKE LOWER(?) OR LOWER(Model) LIKE LOWER(?) OR LOWER(Status) LIKE LOWER(?) OR LOWER(Location) LIKE LOWER(?) OR LOWER(Company) LIKE LOWER(?)";
        Object[] params = new Object[]{"%" + query.toLowerCase() + "%", "%" + query.toLowerCase() + "%", "%" + query.toLowerCase() + "%", "%" + query.toLowerCase() + "%", "%" + query.toLowerCase() + "%"};
        return jdbcTemplate.query(sql, params, rowMapper);
    }

    @Override
    public List<AdminInventoryItem> searchByQuery(String query, String type, String status) {
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
    public Optional<AdminInventoryItem> findById(int id) {
        String sql = "SELECT * FROM Inventory WHERE ItemID = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, rowMapper).stream().findFirst();
    }

    @Override
    public void save(AdminInventoryItem item) {
        String sql = "UPDATE Inventory SET Type = ?, Model = ?, Status = ?, Location = ?, Company = ?, AcquisitionDate = ?, Stock = ? WHERE ItemID = ?";
        jdbcTemplate.update(sql, item.getType(), item.getModel(), item.getStatus(), item.getLocation(), item.getCompany(), java.sql.Timestamp.valueOf(item.getAcquisitionDate()), item.getStock(), item.getItemId());
    }


    @Override
    public void delete(int itemId) {
        String sql = "DELETE FROM Inventory WHERE ItemID = ?";
        jdbcTemplate.update(sql, itemId);
    }

    @Override
    public void saveNewItem(AdminInventoryItem item) {
        String sql = "INSERT INTO Inventory (Type, Model, Status, Location, Stock, Description, Company, AcquisitionDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, item.getType(), item.getModel(), item.getStatus(), item.getLocation(), item.getStock(), item.getDescription(), item.getCompany(), java.sql.Timestamp.valueOf(item.getAcquisitionDate()));
    }
}


