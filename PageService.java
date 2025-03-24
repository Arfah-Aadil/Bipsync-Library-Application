package library.libraryapp.searchbar;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PageService {
    private static final List<Page> pages = new ArrayList<>();

    static {
        pages.add(new Page("Inventory", "/AdminInventory.html"));
        pages.add(new Page("User Management", "/userManage.html"));
        pages.add(new Page("Settings", "/settings"));
        pages.add(new Page("E-mail Management", "/userManage/renewal.html"));
        pages.add(new Page("Forgot Password", "/settings.html"));
        pages.add(new Page("Add Item", "/AdminInventory.html"));
        pages.add(new Page("Delete Item", "/AdminInventory.html"));
        pages.add(new Page("Approve Loan", "/AdminInventory.html"));
        pages.add(new Page("Deny Loan", "/AdminInventory.html"));
        pages.add(new Page("Update Item", "/AdminInventory.html"));
        pages.add(new Page("Change Password", "/settings"));
        pages.add(new Page("Delete User", "/userManage.html"));
        pages.add(new Page("Create User", "/create.html"));

    }
    public List<Page> searchPages(String query) {
        return pages.stream()
                .filter(page -> page.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
