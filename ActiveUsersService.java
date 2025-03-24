package library.libraryapp.activeusers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActiveUsersService {
    @Autowired private ActiveUsersDAO activeUsersDAO;
    public int getActiveUsersSum(){
        return activeUsersDAO.ActiveUsersSum();
    }
}
