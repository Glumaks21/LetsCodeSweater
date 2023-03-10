package springboot.study.letscodesweater.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import springboot.study.letscodesweater.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByActivationCode(String code);

    @Query("SELECT COUNT(u.subscribers) FROM User u WHERE u.id = :id")
    Long countSubscribersById(Long id);

    @Query("SELECT COUNT(u.subscriptions) FROM User u WHERE u.id = :id")
    Long countSubscriptionsById(Long id);
}
