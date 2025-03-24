package library.libraryapp.Translogs;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranslogsService {
    @Autowired
    private TranslogsDAO translogsDAO;
    public List<Translogs> getAllTransactions(){
        return translogsDAO.getAllTransactions();
    }
}
