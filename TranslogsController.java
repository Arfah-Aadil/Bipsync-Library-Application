package library.libraryapp.Translogs;


import library.libraryapp.activeusers.ActiveUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TranslogsController {
    @Autowired
    private TranslogsService translogsService;
    @Autowired ActiveUsersService activeUsersService;
    @GetMapping("/transactions")
    public String seeTransactions(Model model){
        List<Translogs> transactions = translogsService.getAllTransactions();
        model.addAttribute("transactions", transactions);
        int ActiveUsersSum = activeUsersService.getActiveUsersSum();
        model.addAttribute("activeUserSum",ActiveUsersSum);
        return "transactions";
    }

}
