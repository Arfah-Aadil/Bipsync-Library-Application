package library.libraryapp.Login;
import library.libraryapp.*;
import  org.springframework.data.jpa.repository.JpaRepository;
public interface LoginUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
