package library.libraryapp.unit;

import library.libraryapp.model.userInventoryItem;
import library.libraryapp.repository.jdbcUserInventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class JdbcUserInventoryRepositoryTest {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private jdbcUserInventoryRepository repository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {

    }

    @Test
    public void testSearchByQuery() {
        String query = "testtype";
        String type = "testmodel";
        String status = "teststatus";

        repository.searchByQuery(query, type, status);

        verify(jdbcTemplate, times(1)).query(
                eq("SELECT * FROM Inventory WHERE 1=1 AND (LOWER(Type) LIKE LOWER(?) OR LOWER(Model) LIKE LOWER(?) OR LOWER(Status) LIKE LOWER(?) OR LOWER(Location) LIKE LOWER(?) OR LOWER(Company) LIKE LOWER(?)) AND LOWER(Type) = LOWER(?) AND LOWER(Status) = LOWER(?)"),
                eq(new Object[]{"%testtype%", "%testtype%", "%testtype%", "%testtype%", "%testtype%", "testmodel", "teststatus"}),
                any(RowMapper.class)
        );
    }
}