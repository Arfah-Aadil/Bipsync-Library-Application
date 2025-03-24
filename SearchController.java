package library.libraryapp.searchbar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private PageService pageService;

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        List<Page> results = pageService.searchPages(query);
        model.addAttribute("results", results);
        return "transactions";
    }

}
