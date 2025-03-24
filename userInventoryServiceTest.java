package library.libraryapp.unit;

import library.libraryapp.model.userInventoryItem;
import library.libraryapp.repository.userInventoryRepository;
import library.libraryapp.service.UserInventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class userInventoryServiceTest {

    @Mock
    private userInventoryRepository repository;

    @InjectMocks
    private UserInventoryService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllInventoryItems() {
        List<userInventoryItem> mockItems = List.of(new userInventoryItem(/* parameters */));
        when(repository.findAll()).thenReturn(mockItems);

        List<userInventoryItem> items = service.findAllInventoryItems();
        assertEquals(mockItems, items);

        verify(repository, times(1)).findAll();
    }

    @Test
    public void testFindAllInventoryItems_Empty() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<userInventoryItem> items = service.findAllInventoryItems();
        assertEquals(Collections.emptyList(), items);

        verify(repository, times(1)).findAll();
    }
}