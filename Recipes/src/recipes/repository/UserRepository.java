package recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import recipes.entities.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

}