package library.libraryapp.component;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import library.libraryapp.controller.userInventoryController;
import library.libraryapp.model.userInventoryItem;
import library.libraryapp.service.UserInventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

@WebMvcTest(userInventoryController.class)
public class userInventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserInventoryService service;

    @Test
    public void getAllInventoryItems_Success() throws Exception {
        List<userInventoryItem> mockItems = List.of(new userInventoryItem(/* parameters */));
        when(service.findAllInventoryItems()).thenReturn(mockItems);

        mockMvc.perform(get("/userInventory"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("items"))
                .andExpect(view().name("userInventory"));

        verify(service, times(1)).findAllInventoryItems();
    }

    @Test
    public void getAllInventoryItems_Error() throws Exception {
        when(service.findAllInventoryItems()).thenThrow(new RuntimeException("Test exception"));

        mockMvc.perform(get("/userInventory"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("items", Collections.emptyList()))
                .andExpect(view().name("userInventory"));

        verify(service, times(1)).findAllInventoryItems();
    }
}