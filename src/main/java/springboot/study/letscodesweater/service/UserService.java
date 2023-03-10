package springboot.study.letscodesweater.service;

import org.springframework.data.jpa.repository.Query;
import springboot.study.letscodesweater.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserByUsername(String username);
    void registerUser(String login, String email, String password);
    boolean activateUser(String activationCode);
    void subscribe(User channel, User newSubscriber);
    void unsubscribe(User channel, User newSubscriber);
    void editUser(User user, String email, String password);
    void updateUser(User user);

    Long getSubscribersCountById(Long id);

    Long getSubscriptionsCountById(Long id);
}
