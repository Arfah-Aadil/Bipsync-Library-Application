package library.libraryapp.unit;

import library.libraryapp.model.userTransaction;
import library.libraryapp.repository.jdbcUserTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(jdbcUserTransactionRepository.class)
public class jdbcUserTransactionRepositoryTest {

    @Autowired
    private jdbcUserTransactionRepository repository;

    private userTransaction transaction;

    @BeforeEach
    public void setUp() {
        transaction = new userTransaction();
        transaction.setItemId(1);
        transaction.setUserId(1);
        transaction.setCheckoutDate(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        transaction.setDueDate(LocalDate.now().plusDays(14));
        transaction.setCheckinDate(null);
        transaction.setStatus("Checked Out");
    }

    @Test
    public void testSave() throws SQLException {
        repository.save(transaction);

        List<userTransaction> transactions = repository.findAll();
        assertFalse(transactions.isEmpty(), "Transactions list should not be empty");

        userTransaction savedTransaction = transactions.stream()
                .filter(t -> t.getItemId() == transaction.getItemId() && t.getUserId() == transaction.getUserId())
                .findFirst()
                .orElse(null);

        assertNotNull(savedTransaction, "Saved transaction should not be null");
        assertEquals(transaction.getItemId(), savedTransaction.getItemId());
        assertEquals(transaction.getUserId(), savedTransaction.getUserId());
        assertEquals(transaction.getCheckoutDate().truncatedTo(ChronoUnit.MILLIS), savedTransaction.getCheckoutDate().truncatedTo(ChronoUnit.MILLIS));
        assertEquals(transaction.getDueDate(), savedTransaction.getDueDate());
        assertNull(savedTransaction.getCheckinDate());
        assertEquals(transaction.getStatus(), savedTransaction.getStatus());
    }
}